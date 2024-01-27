package ru.karpenko.practice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.karpenko.practice.models.Book;
import ru.karpenko.practice.models.Person;
import ru.karpenko.practice.repositories.BooksRepository;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.text.SimpleDateFormat;

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

    public List<Book> searchByName (String name){
        return booksRepository.findByNameStartingWith(name);
    }

    @Transactional
    public void appointBook(int id, Person person){
        System.out.println("start");
        Optional<Book> foundBooks = booksRepository.findById(id);
        Book book = foundBooks.orElse(null);
        book.setOwner(person);
        book.setAppointTime(new Date());
        booksRepository.save(book);
        System.out.println("end");
    }
    @Transactional
    public void releaseBook(int id){
        Optional<Book> foundBooks = booksRepository.findById(id);
        Book book = foundBooks.orElse(null);
        book.setOwner(null);
        book.setAppointTime(null);
        booksRepository.save(book);
    }
    public List<Book> checkOverdue(List<Book> books) throws ParseException {
        for (Book book : books) {
            if (book.getAppointTime() != null) {
                java.time.LocalDateTime limitDateTime = java.time.LocalDateTime.now();
                limitDateTime = limitDateTime.minusDays(10);
                String[] parts = limitDateTime.toString().split("T");
                String limDate = parts[0];
                String[] parts2 = book.getAppointTime().toString().split(" ");
                String appointTime = parts2[0];
                Date appointTimeDate = new SimpleDateFormat("dd-MM-yyyy").parse(appointTime);
                Date limDateT = new SimpleDateFormat("dd-MM-yyyy").parse(limDate);
                if ( appointTimeDate.before(limDateT)) {
                    book.setOverdue(true);
                }
            }
        }
        return books;
    }

}
