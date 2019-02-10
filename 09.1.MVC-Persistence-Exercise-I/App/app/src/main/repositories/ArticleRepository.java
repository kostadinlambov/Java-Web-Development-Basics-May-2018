package repositories;

import entities.Article;
import entities.User;

import javax.persistence.NoResultException;
import java.util.List;

public class ArticleRepository extends BaseRepository {
    public List<Article> findAll() {
        List<Article> result = (List<Article>) executeAction(repositoryActionResult -> {
            repositoryActionResult.setResult(
                    this.entityManager.createNativeQuery(
                            "SELECT * FROM articles", Article.class)
                            .getResultList());
        }).getResult();

        return result;
    }

    public Article findById(String articleId) {
        Article result = (Article) executeAction(repositoryActionResult -> {
            Article articleFromDatabase;

            try {
                articleFromDatabase = (Article) this.entityManager.createNativeQuery(

                        "SELECT * FROM articles WHERE id = '"
                                + articleId
                                + "'", Article.class)
                        .getSingleResult();
            } catch (NoResultException ignored) {
                articleFromDatabase = null;
            }

            repositoryActionResult.setResult(articleFromDatabase);
        }).getResult();

        return result;
    }

    public void saveArticle(Article article) {
        executeAction(repositoryActionResult -> {
            this.entityManager.persist(article);
        });
    }
}
