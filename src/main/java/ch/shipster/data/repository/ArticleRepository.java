package ch.shipster.data.repository;

import ch.shipster.data.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Timo

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article getArticleById(Long id);

    List<Article> findAllByName(String name);
}
