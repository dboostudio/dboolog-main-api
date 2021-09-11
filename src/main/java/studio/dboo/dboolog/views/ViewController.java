package studio.dboo.dboolog.views;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/view")
public class ViewController {

    @GetMapping("/")
    public String index(Model model, Principal principal){
        transferUsernameToModel(model, principal);
        return "index";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model){
        return "sign-up";
    }

    @GetMapping("/login")
    public String login(Model model){ return "login"; }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal){
        transferUsernameToModel(model, principal);
        return "admin";
    }

    @GetMapping("/articles")
    public String article(Model model, Principal principal){
        return "articles/articles";
    }

    private void transferUsernameToModel(Model model, Principal principal) {
        if(principal == null){
            model.addAttribute("userId", "Welcome To Dboo's Log");
        } else {
            model.addAttribute("userId", principal.getName());
        }
    }

}
