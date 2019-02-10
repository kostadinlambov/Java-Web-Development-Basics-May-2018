package main.controllers;

import main.entities.Article;
import main.entities.User;
import main.models.binding.ArticleCreateBindingModel;
import main.repositories.ArticleRepository;
import main.repositories.UserRepository;
import org.softuni.broccolina.solet.HttpSoletRequest;
import org.softuni.summermvc.api.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        return "template:article-create";
    }

    @PostMapping(route = "/articles/create")
    public String createArticleConfirm(HttpSoletRequest request, ArticleCreateBindingModel articleCreateBindingModel) {
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        String userId = request.getSession().getAttributes().get("user-id").toString();

        User currentUser = this.userRepository.findById(userId);

        Article article = new Article();

        article.setTitle(articleCreateBindingModel.getTitle());
        article.setContent(articleCreateBindingModel.getContent());
        article.setCreatedOn(LocalDateTime.now());
        article.setAuthor(currentUser);

        this.articleRepository.saveArticle(article);

        return "redirect:/home";
    }

    @GetMapping(route = "/articles/all")
    public String allArticles(HttpSoletRequest request, Model model) {
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        List<Article> articles = this.articleRepository.findAll();

        StringBuilder resultHtml = new StringBuilder();

        int currentIndex = 0;

        for (Article article : articles) {
            resultHtml
                    .append("<tr class=\"row\">")
                    .append("<th class=\"col-md-1\" scope=\"row\">"
                            + (++currentIndex) + "</th>")
                    .append("<td class=\"col-md-2\">"
                            + article.getTitle() + "</td>")
                    .append("<td class=\"col-md-3\">"
                            + (article.getContent().length() > 30
                            ? article.getContent().substring(0, 27) + "..."
                            : article.getContent())
                            + "</td>")
                    .append("<td class=\"col-md-2\">"
                            + article
                            .getCreatedOn()
                            .format(DateTimeFormatter
                                    .ofPattern("HH:mm dd-MM-yyyy"))
                            + "</td>")
                    .append("<td class=\"col-md-2\">"
                            + article
                            .getAuthor()
                            .getUsername() + "</td>")
                    .append("<td class=\"col-md-2\">"
                            + "<a class=\"btn btn-secondary\" href=\"/articles/details/"
                            + article.getId() + "\">"
                            + "Details"
                            + "</a>"
                            + "</td>")
                    .append("</tr>");
        }

        model.addAttribute("articles", resultHtml.toString());

        return "template:article-all";
    }

    @GetMapping(route = "/articles/details/{id}")
    public String articleDetails(@PathVariable(name = "id") String id, Model model, HttpSoletRequest request) {
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        Article article = this.articleRepository.findById(id);

        if (article == null) {
            return "redirect:/articles/all";
        }

        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());
        model.addAttribute("createdOn", article.getCreatedOn()
                .format(DateTimeFormatter
                        .ofPattern("HH:mm dd-MM-yyyy")));
        model.addAttribute("author", article.getAuthor().getUsername());

        String articleAuthorId = article.getAuthor().getId();
        String currentUserId = request.getSession().getAttributes().get("user-id").toString();
        String currentUserRole = request.getSession().getAttributes().get("role").toString();

        String actionsBlock = "";

        if (articleAuthorId.equals(currentUserId) || currentUserRole.equals("ADMIN")) {
            actionsBlock = "<hr class=\"bg-secondary half-width\"/>"
                    + "<div class=\"actions-block mx-auto width-15 d-flex justify-content-between\">"
                    + "<a href=\"/articles/edit/"
                    + article.getId()
                    + "\"class=\"btn btn-secondary\">Edit</a>"
                    + "<a href=\"/articles/delete/"
                    + article.getId()
                    + "\" class=\"btn btn-secondary\">Delete</a>"
                    + "</div>";
        }

        model.addAttribute("actionsBlock", actionsBlock);

        return "template:article-details";
    }

    @GetMapping(route = "/articles/edit/{id}")
    public String edit(@PathVariable(name = "id") String id, Model model, HttpSoletRequest request) {
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        Article article = this.articleRepository.findById(id);

        if (article == null || (!article.getAuthor().getId().equals(request.getSession().getAttributes().get("user-id").toString()) && !request.getSession().getAttributes().get("role").toString().equals("ADMIN"))) {
            return "redirect:/articles/all";
        }

        model.addAttribute("id", article.getId());
        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());

        return "template:article-edit";
    }

    @PostMapping(route = "/articles/edit/{id}")
    public String editConfirm(@PathVariable(name = "id") String id, HttpSoletRequest request, ArticleCreateBindingModel articleCreateBindingModel) {
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        Article article = this.articleRepository.findById(id);

        if (article == null || (!article
                .getAuthor()
                .getId().equals(
                        request.getSession()
                                .getAttributes()
                                .get("user-id")
                                .toString()) && !request.getSession().getAttributes().get("role").toString().equals("ADMIN"))) {
            return "redirect:/articles/all";
        }

        article.setTitle(articleCreateBindingModel.getTitle());
        article.setContent(articleCreateBindingModel.getContent());

        this.articleRepository.updateArticle(article);

        return "redirect:/articles/details/" + article.getId();
    }

    @GetMapping(route = "/articles/delete/{id}")
    public String delete(@PathVariable(name = "id") String id, Model model, HttpSoletRequest request) {
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        Article article = this.articleRepository.findById(id);

        if (article == null || (!article.getAuthor().getId().equals(request.getSession().getAttributes().get("user-id").toString()) && !request.getSession().getAttributes().get("role").toString().equals("ADMIN"))) {
            return "redirect:/articles/all";
        }

        model.addAttribute("id", article.getId());
        model.addAttribute("title", article.getTitle());
        model.addAttribute("content", article.getContent());
        model.addAttribute("createdOn", article.getCreatedOn());
        model.addAttribute("author", article.getAuthor().getUsername());

        return "template:article-delete";
    }

    @PostMapping(route = "/articles/delete/{id}")
    public String deleteConfirm(@PathVariable(name = "id") String id, HttpSoletRequest request) {
        if (request.getSession() == null) {
            return "redirect:/login";
        }

        Article article = this.articleRepository.findById(id);

        if (article == null || (!article
                .getAuthor()
                .getId().equals(
                        request.getSession()
                                .getAttributes()
                                .get("user-id")
                                .toString()) && !request.getSession().getAttributes().get("role").toString().equals("ADMIN"))) {
            return "redirect:/articles/all";
        }

        this.articleRepository.deleteArticle(article);

        return "redirect:/articles/all";
    }
}
