package ru.karpenko.practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpenko.practice.models.Genre;
import ru.karpenko.practice.repositories.GenresRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class GenresService {
    private final GenresRepository genresRepository;

    @Autowired
    public GenresService(GenresRepository genresRepository) {
        this.genresRepository = genresRepository;
    }

    public List<Genre> findAll(){
        return genresRepository.findAll();
    }
}
