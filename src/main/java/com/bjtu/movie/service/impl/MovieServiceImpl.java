package com.bjtu.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bjtu.movie.constants.Sort;
import com.bjtu.movie.entity.Movie;
import com.bjtu.movie.mapper.MovieMapper;
import com.bjtu.movie.exception.ServiceException;
import com.bjtu.movie.model.MovieCalender;
import com.bjtu.movie.service.IMovieService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.bjtu.movie.utils.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 所有影片信息 服务实现类
 * </p>
 *
 * @author Jinxuan Chen
 * @since 2024-05-20
 */
@Service
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements IMovieService {

    @Autowired
    private MovieMapper movieMapper;

    @Autowired
    private TotalServiceImpl totalService;

    @Autowired
    private PosterServiceImpl posterService;

    @Override
    public Page<Map<String,Object>> getAllMovies(Integer currentPage, Integer pageSize) {
//        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(Movie::isDeleted,false)
//                .select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime);
//        return movieMapper.selectMapsPage(new Page<>(currentPage,pageSize),wrapper);
        return movieMapper.getAllMovies(new Page<>(currentPage,pageSize));
    }

    @Override
    public Movie getAMovieByID(Integer id) {
        return movieMapper.getAMovieByID(id);
    }

    @Override
    public Movie getAMovieByImdbID(String imdbId) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Movie::getImdbId,imdbId)
                .eq(Movie::isDeleted,false);
        return getOne(wrapper);
    }

    private boolean isAsc(String order){
        if(order.equals(Sort.ORDER_BY_ASC.getValue())){
            return true;
        } else if(order.equals(Sort.ODER_BY_DESC.getValue())){
            return false;
        }else{
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "错误的顺序标识符");
        }
    }

    @Override
    public Page<Map<String, Object>> getMoviesByIndex(Integer currentPage, Integer pageSize,
                                                      String sort, String order,
                                                      List<String> genres,
                                                      String startYear, String endYear,
                                                      Double ratingFrom, Double ratingTo,
                                                      Integer votesFrom, Integer votesTo,
                                                      List<String> keywords) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Movie::isDeleted,false);

        //排序
        if(!Objects.isNull(sort)) {
            wrapper.orderBy(sort.equals(Sort.SORT_BY_RATING.getValue()), isAsc(order), Movie::getVoteAverage);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_NUMBER_OF_RATINGS.getValue()), isAsc(order), Movie::getVoteCount);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_RELEASE_DATE.getValue()), isAsc(order), Movie::getReleaseDate);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_POPULARITY.getValue()), isAsc(order), Movie::getPopularity);
            wrapper.orderBy(sort.equals(Sort.SORT_BY_RUNTIME.getValue()), isAsc(order), Movie::getRuntime);
        }

        //筛选分类
        if(!Objects.isNull(genres)){
            for (String i: genres) {
                wrapper.apply("JSON_CONTAINS(JSON_EXTRACT(genres, '$[*].name'), CAST('\""+i+"\"' AS JSON), '$')");
            }
        }

        //筛选年份
        if (!Objects.isNull(startYear) && !Objects.isNull(endYear)){
            Date startDate = DateTimeUtil.getYearStartDate(startYear);
            Date endDate = DateTimeUtil.getYearEndDate(endYear);
            wrapper.between(Movie::getReleaseDate,startDate,endDate);
        }else if(!Objects.isNull(startYear)){
            Date startDate = DateTimeUtil.getYearStartDate(startYear);
            wrapper.ge(Movie::getReleaseDate,startDate);
        }else if(!Objects.isNull(endYear)){
            Date endDate = DateTimeUtil.getYearEndDate(endYear);
            wrapper.le(Movie::getReleaseDate,endDate);
        }

        //筛选评分
        if(!Objects.isNull(ratingFrom) && !Objects.isNull(ratingTo)){
            wrapper.between(Movie::getVoteAverage,ratingFrom,ratingTo);
        }else if(!Objects.isNull(ratingFrom)){
            wrapper.ge(Movie::getVoteAverage,ratingFrom);
        }else if(!Objects.isNull(ratingTo)){
            wrapper.le(Movie::getVoteAverage,ratingTo);
        }

        //筛选评分人数
        if(!Objects.isNull(votesFrom) && !Objects.isNull(votesTo)){
            wrapper.between(Movie::getVoteCount,votesFrom,votesTo);
        }else if(!Objects.isNull(votesFrom)){
            wrapper.ge(Movie::getVoteCount,votesFrom);
        }else if(!Objects.isNull(votesTo)){
            wrapper.le(Movie::getVoteCount,votesTo);
        }

        //筛选关键词
        if (!Objects.isNull(keywords)){
            for (String i: keywords) {
                wrapper.inSql(Movie::getId,
                        "select id from keywords "
                            + "where JSON_CONTAINS(JSON_EXTRACT(keywords, '$[*].name'), CAST('\""+i+"\"' AS JSON), '$')");
            }
        }

        wrapper.select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime);
        //return movieMapper.selectMapsPage(new Page<>(currentPage,pageSize),wrapper);

        //获取海报
        Page<Map<String,Object>> tmp = movieMapper.selectMapsPage(new Page<>(currentPage,pageSize),wrapper);
        tmp.getRecords().forEach(i ->
                i.put("poster_path", posterService.getPosterPathById((Integer) i.get("id"))));
        return tmp;
    }

    @Override
    public void addNewMovie(Movie movie) {
        if(!isSameMovie(movie)){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "电影已存在");
        }
        totalService.updateMovieTotalPlus();
        movie.setId(totalService.getMovieId());
        movie.setVoteCount(0);
        movie.setVoteAverage(0.0);
        movie.setPopularity(0.0);
        movie.setDeleted(false);
        save(movie);
    }

    private boolean isSameMovie(Movie movie) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(!Objects.isNull(movie.getOriginalTitle()),Movie::getOriginalTitle,movie.getOriginalTitle())
                .eq(!Objects.isNull(movie.getTitle()),Movie::getTitle,movie.getTitle())
                .eq(!Objects.isNull(movie.getProductionCountries()),Movie::getProductionCompanies,movie.getProductionCompanies())
                .eq(!Objects.isNull(movie.getProductionCountries()),Movie::getProductionCountries,movie.getProductionCountries())
                .eq(!Objects.isNull(movie.getBelongsToCollection()),Movie::getBelongsToCollection,movie.getBelongsToCollection())
                .eq(!Objects.isNull(movie.getAdult()),Movie::getAdult,movie.getAdult())
                .eq(!Objects.isNull(movie.getReleaseDate()),Movie::getReleaseDate,movie.getReleaseDate());
        return Objects.isNull(getOne(wrapper));
    }

    @Override
    public void updateAMovieInfo(Movie movie) {
        if(getAMovieByID(movie.getId()) == null){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "电影不存在");
        }
        LambdaUpdateWrapper<Movie> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Movie::getId,movie.getId());
        update(movie,wrapper);
    }

    @Override
    public void deleteAMovie(Integer id) {
        Movie movie = getAMovieByID(id);
        if(movie == null){
            throw new ServiceException(HttpStatus.FORBIDDEN.value(), "电影不存在");
        }
        LambdaUpdateWrapper<Movie> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Movie::getId,movie.getId())
                .set(Movie::isDeleted, true);
        update(wrapper);
    }

    @Override
    public List<Map<String, Object>> getTopNMovie() {
//        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
//        wrapper.select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime)
//                .orderByDesc(Movie::getVoteAverage)
//                .last("LIMIT 100");
//        return movieMapper.selectMaps(wrapper);
        return movieMapper.getTopNMovie();
    }

    @Override
    public List<Map<String, Object>> getMostPopularNMovie() {
//        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
//        wrapper.select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime)
//                .orderByDesc(Movie::getPopularity)
//                .last("LIMIT 50");
//        return movieMapper.selectMaps(wrapper);
        return movieMapper.getMostPopularNMovie();
    }

    @Override
    public Page<MovieCalender> getMovieGroupByDate(Integer currentPage, Integer pageSize) {
        //获取所有电影的日期
        Page<MovieCalender> movieCalenderPage = movieMapper.getMovieGroupByDate(new Page<>(currentPage,pageSize));
        //遍历movieCalenderPage中的所有记录
        for(MovieCalender i: movieCalenderPage.getRecords()){
            //存储电影的信息
            List<Map<String,Object>> movieList = new ArrayList<>();
            //存储电影的ID
            List<Integer> idList = new ArrayList<>();
            //获取当前MovieCalender对象的ids属性（这是一个包含多个电影ID的字符串），然后使用,作为分隔符将其分割成一个字符串数组
            String[] stringIds = i.getIds().split(",");
            //遍历stringIds数组，将每个ID转换为整数，并添加到idList列表中
            for (String id: stringIds){
                idList.add(Integer.valueOf(id));
            }
            //遍历idList中的所有电影ID
            for (Integer j: idList){
                LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(Movie::getId,j)
                        .select(Movie::getId,Movie::getTitle,Movie::getVoteAverage,Movie::getVoteCount,Movie::getReleaseDate,Movie::getRuntime);
                Map<String,Object> tmpMap = getMap(wrapper);
                String posterPath = posterService.getPosterPathById((Integer) tmpMap.get("id"));
                tmpMap.put("poster_path", Objects.requireNonNullElse(posterPath, "null"));
                //执行查询并将结果添加到movieList列表中
                movieList.add(tmpMap);
            }
            //将movieList设置为当前MovieCalender对象的movieList属性
            i.setMovieList(movieList);
        }
        return movieCalenderPage;
    }

    @Override
    public void updateTotalRating(Integer userId, Integer movieId, Double rating) {
        LambdaQueryWrapper<Movie> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Movie::getId,movieId)
                .select(Movie::getVoteAverage,Movie::getVoteCount);
        Movie movie = getOne(wrapper);

        Integer oldVoteCount = movie.getVoteCount();
        Double total = movie.getVoteAverage()*oldVoteCount;

        DecimalFormat df = new DecimalFormat("#.0");
        Double newVoteAverage = (total+rating)/(oldVoteCount+1);
        movie.setId(movieId);
        movie.setVoteAverage(Double.valueOf(df.format(newVoteAverage)));
        movie.setVoteCount(oldVoteCount+1);
        updateAMovieInfo(movie);
    }

    @Override
    public List<Map<String,Object>> getRecommendMovie(Integer id) {
        return null;
    }

    @Override
    public List<Map<String, Object>> getHotRecommendMovie() {
        String pythonPath = "C:\\Users\\84579\\Desktop\\Movie-Recommendation-System\\simpleIMDB.py";
//      String pythonPath = "C:\\Codefield\\CODE_PYTHON\\Movie-Recommendation-System\\content-plotDescription.py";
//      String pythonPath = "C:\\Codefield\\CODE_PYTHON\\Movie-Recommendation-System\\content-2.py";
//      String pythonPath = "C:\\Codefield\\CODE_PYTHON\\Movie-Recommendation-System\\collaborative-filtering.py";
        String[] arguments = new String[] {"python",pythonPath};//指定命令、路径、传递的参数
//      String[] arguments = new String[] {"python",pythonPath,String.valueOf(238)};//指定命令、路径、传递的参数
//      String[] arguments = new String[] {"python",pythonPath,String.valueOf(238)};//指定命令、路径、传递的参数
//      String[] arguments = new String[] {"python",pythonPath,String.valueOf(1)};//指定命令、路径、传递的参数
        StringBuilder sbrs = null;
        StringBuilder sberror = null;
        try {
            ProcessBuilder builder = new ProcessBuilder(arguments);
            Process process = builder.start();
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream(), "utf-8"));//获取字符输入流对象
            BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream(), "utf-8"));//获取错误信息的字符输入流对象
            String line = null;
            sbrs = new StringBuilder();
            sberror = new StringBuilder();
            //记录输出结果
            while ((line = in.readLine()) != null) {
                sbrs.append(line);
            }
            //记录错误信息
            while ((line = error.readLine()) != null) {
                sberror.append(line);
            }
            in.close();
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(sbrs);
        System.out.println(sberror);

        ArrayList<Integer> integerList = new ArrayList<>();
        if (sbrs != null) {
            // 去除方括号并分割字符串
            String content = sbrs.toString().trim();
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(content);
            while (matcher.find()) {
                integerList.add(Integer.parseInt(matcher.group()));
            }
        }
        System.out.println(integerList);
        List<Map<String,Object>> result = new ArrayList<>();
        for (Integer id : integerList) {
            Map<String,Object> tmp = getMovieBriefById(id);
            if(tmp != null) {
                result.add(tmp);
            }
        }

        System.out.println(sberror);
        return result;
    }

    @Override
    public Map<String, Object> getMovieBriefById(Integer id) {
        return movieMapper.getMovieBriefById(id);
    }

    @Override
    public List<Map<String,Object>> getRelatedRecommendMovie(Integer id) {
        return null;
    }
}
