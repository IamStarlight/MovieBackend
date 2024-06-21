package com.bjtu.movie.controller;


import com.bjtu.movie.entity.Genres;
import com.bjtu.movie.model.Result;
import com.bjtu.movie.service.impl.GenresServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
public class GenresController {

    @Autowired
    private GenresServiceImpl genresService;

    /**
     * 增加一个类别
     * @return
     */
    @PostMapping
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> addAGenres(@RequestBody Genres genres){
        genresService.addAGenres(genres);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }


    /**
     * 修改一个类别
     * @return
     */
    @PutMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> modifyAGenres(@PathVariable Integer id,
                                                @RequestBody Genres genres){
        genres.setId(id);
        genresService.modifyAGenres(genres);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }

    /**
     * 获取所有类别
     * @return
     */
    @GetMapping
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> getAllGenres(){
        return new ResponseEntity<>(Result.success(genresService.getAllGenres()), HttpStatus.OK);
    }

    /**
     * 删除一个类别
     * @return
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAnyAuthority('ROLE_SUPER_ADMIN','ROLE_ADMIN','ROLE_USER')")
    public ResponseEntity<Result> deleteGenresById(@PathVariable Integer id){
        genresService.deleteGenresById(id);
        return new ResponseEntity<>(Result.success(), HttpStatus.OK);
    }
}
