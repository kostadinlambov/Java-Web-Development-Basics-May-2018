package controllers;

import entities.Article;
import entities.User;
import models.binding.ArticleCreateBindingModel;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.javache.http.HttpSession;
import org.softuni.summermvc.api.Controller;
import org.softuni.summermvc.api.GetMapping;
import org.softuni.summermvc.api.Model;
import org.softuni.summermvc.api.PostMapping;
import repositories.ArticleRepository;
import repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ArticleController {
    private ArticleRepository articleRepository;

    private UserRepository userRepository;

    public ArticleController() {
        this.articleRepository = new ArticleRepository();
        this.userRepository = new UserRepository();
    }

    @GetMapping(route = "/articles/create")
    public String createArticle(HttpSoletRequest request) {
//        if (request.getSession() == null) {
//            return "redirect:/login";
//        }

        return "template:article-create";
    }

    @PostMapping(route = "/articles/create")
    public String createArticleConfirm(HttpSession session, ArticleCreateBindingModel articleCreateBindingModel) {
//        if (session == null) {
//            return "redirect:/login";
//        }

        String userId = session.getAttributes().get("user-id").toString();

        User currentUser = this.userRepository.findById(userId);

        Article article = new Article();

        article.setTitle(articleCreateBindingModel.getTitle());
        article.setContent(articleCreateBindingModel.getContent());
        article.setCreatedOn(LocalDate.now());
        article.setAuthor(currentUser);

        this.articleRepository.saveArticle(article);

        System.out.println(article);

        return "redirect:/home";
    }

    @GetMapping(route = "/articles/all")
    public String allArticles(HttpSoletRequest request, Model model) {
//        if (request.getSession() == null) {
//            return "redirect:/login";
//        }

        List<Article> articles = this.articleRepository.findAll();

        StringBuilder resultHtml = new StringBuilder();

        /*
        <tr class="row">
                <th class="col-md-2" scope="row">1</th>
                <td class="col-md-2" scope="row">asdasd</td>
                <td class="col-md-6" scope="row">dddddddddddddddddddddddddddddddd</td>
                <td class="col-md-2" scope="row">Pesho</td>
            </tr>
         */

        int currentIndex = 0;

        for (Article article : articles) {
            resultHtml
                    .append("<tr class=\"row\">")
                    .append("<th class=\"col-md-2\" scope=\"row\">"
                            + (++currentIndex) + "</th>")
                    .append("<td class=\"col-md-2\" scope=\"row\">"
                            + article.getTitle() + "</td>")
                    .append("<td class=\"col-md-6\" scope=\"row\">"
                            + (article.getContent().length() > 30
                            ? article.getContent().substring(0, 27) + "..."
                            : article.getContent())
                            + "</td>")
                    .append("<td class=\"col-md-2\" scope=\"row\">"
                            + article
                            .getAuthor()
                            .getUsername() + "</td>")
                    .append("</tr>");
        }

        model.addAttribute("articles", resultHtml.toString());

        return "template:article-all";
    }
}
