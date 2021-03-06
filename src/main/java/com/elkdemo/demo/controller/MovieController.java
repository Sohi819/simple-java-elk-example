package com.elkdemo.demo.controller;

import com.elkdemo.demo.model.MovieRating;
import com.elkdemo.demo.model.Response;
import com.elkdemo.demo.service.MovieService;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/movie")
public class MovieController {

    private final MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/rate", method = RequestMethod.POST)
    public Response<MovieRating> saveMovieRating(@RequestBody MovieRating movieRating) {
        try {
            movieRating.setDateOfReview(new Date());
            MovieRating savedMovieRating = movieService.saveMovieRating(movieRating);

            return Response.ok(savedMovieRating, "New Movie saved");
        } catch (Exception e) {
            return Response.totallyNotOk(movieRating, "We were unable to save your rating at this time!");
        }
    }

    @RequestMapping(value = "/{fieldName}/{fieldValue}", method = RequestMethod.GET)
    public Response<List<MovieRating>> getMoviesWithProperyLike(@PathVariable String fieldName, @PathVariable String fieldValue) {
        return Response.ok(movieService.findMoviesWithPropertiesLike(fieldName, fieldValue), "Movies with fieldName "
                + fieldName + " similar to " + fieldValue);
    }
}
