package pl.b2bnetwork.service;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.b2bnetwork.model.Movie;
import pl.b2bnetwork.repository.MovieRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) throws NotFoundException {
        return movieRepository.findOne(validateMovie(id));
    }

    public List<Movie> getMoviesByTitle(String movieTitle) {
        return movieRepository.findByTitle(movieTitle);
    }

    public List<Movie> getMoviesByYear(Integer year) {
        return movieRepository.findByYear(year);
    }

    public void addMovie(Movie movie) {
        movieRepository.save(movie);
    }

    public String countMovies() {
        return movieRepository.countAllMovies();
    }

    private Long validateMovie(Long movieId) throws NotFoundException {
        return Optional.ofNullable(movieRepository.findOne(movieId))
                       .map(movie -> movie.getId())
                       .orElseThrow(() -> new NotFoundException("Movie not found"));
    }

    public void updateMovie(Long movieId, Movie movie) throws NotFoundException {
        Long id = validateMovie(movieId);
        movie.setId(id);
        movieRepository.save(movie);
    }
}
