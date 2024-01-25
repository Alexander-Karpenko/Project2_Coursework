package ru.karpenko.practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpenko.practice.models.Book;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.repositories.BooksRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BooksService {
    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public List<Book> findAll(){
        return booksRepository.findAll();
    }

    public Book findOne(int id){
        Optional<Book> foundBooks = booksRepository.findById(id);
        return foundBooks.orElse(null);
    }
    @Transactional
    public void save(Book book){
        booksRepository.save(book);
    }
    @Transactional
    public void update(int id, Book updatedBook){
        updatedBook.setId(id);
        booksRepository.save(updatedBook);
    }
    @Transactional
    public void delete(int id){

        booksRepository.deleteById(id);
    }
    public List<Book> findByOwner(Person person){
        return booksRepository.findByOwner(person);
    }

  public List<Book> findAll(int pageNum, int size){
//      Pageable page =  PageRequest.of(pageNum, size);
//      return booksRepository.findAll(page);
      return booksRepository.findAll(PageRequest.of(pageNum, size)).getContent();
  }
  public List<Book> findAll(boolean sort){
        if (sort == true){
           return booksRepository.findAll(Sort.by("yearOfIssue"));
        }else {
            return booksRepository.findAll();
        }
  }
    public List<Book> findAll(int pageNum, int size, boolean sort){
        if (sort == true){
            return booksRepository.findAll(PageRequest.of(pageNum, size, Sort.by("yearOfIssue"))).getContent() ;
        }else {
            return booksRepository.findAll();
        }
    }


}
