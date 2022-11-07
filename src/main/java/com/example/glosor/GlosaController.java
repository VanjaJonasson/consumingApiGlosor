package com.example.glosor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Controller // This means that this class is a Controller
//@RequestMapping(path="/") // This means URL's start with / (after Application path)
public class GlosaController {

    /*
    @Autowired // This means to get the bean called userRepository
    Which is auto-generated by Spring, we will use it to handle the data
    private GlosaRepository glosaRepository
     */
    @Autowired
    private GlosaService glosaService;

    @Autowired
    private PlayerRepository playerrepository;

    @GetMapping("/add")
    public String add(Model model, RestTemplate restTemplate) {
        model.addAttribute("glosa", new Glosa());
        List<Category> categories = restTemplate.getForObject("http://localhost:8081/categories", ArrayList.class);
        model.addAttribute("category", categories);
        return "addGlosa";
    }

    @PostMapping("/save")
    public String addNewGlosa(@ModelAttribute Glosa glosa, @RequestParam String category, RestTemplate restTemplate) {
        //Integer num = Integer.valueOf(category);
        System.out.println("om glosa är ny: " + glosa.isNew()); //isNew funkar
        if (glosa.isNew()) {
            restTemplate.postForObject("http://localhost:8081/glosa/" + category, glosa, Glosa.class);
        }
        else {
            restTemplate.put("http://localhost:8081/glosa/" + category, glosa, Glosa.class);
        }

        return "redirect:/showall";
    }

    @GetMapping("/edit/{id}")
    public String editByName(Model model, @PathVariable Integer id, RestTemplate restTemplate) {
        model.addAttribute("glosa", glosaService.glosaToEdit(id));
        int val = 1;
        model.addAttribute("val", val);
        List<Category> categories = restTemplate.getForObject("http://localhost:8081/categories", ArrayList.class);
        model.addAttribute("category", categories);
        return "addGlosa";
    }

    @GetMapping("/showall")
    public String showall(Model model) {
        model.addAttribute("glosor", glosaService.getAllGlosor());
        return "alla";
    }

    @GetMapping("/showone")
    public String showone(Model model, RestTemplate restTemplate) {
        Glosa enGlosa = restTemplate.getForObject("http://localhost:8081/glosa/3", Glosa.class);
        model.addAttribute("glosa", enGlosa);
        return "visaEn";
    }

    @GetMapping("/all")
    //The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    public @ResponseBody
    List<Glosa> getAllGlosor() {
        // This returns a JSON or XML with the users
        return (List<Glosa>) glosaService.getAllGlosor();
    }


 /*
    @GetMapping("/categories")
    public @ResponseBody
    List<Category> allaKategorier() {
        return (List<Category>) categoryrepository.findAll();
    }
*/


    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        glosaService.deleteGlosa(id);
        return "redirect:/showall";
    }

    @GetMapping("/numq")
    public String numOfQuestions(HttpSession session, @RequestParam int num) {
        session.setAttribute("numQuestions", num);
        System.out.println("sparat nummer: " + num);
        return "redirect:/start";
    }

    @GetMapping("/home")
    public String home(HttpSession session) {

        System.out.println("testing!!" + glosaService.getCatName(3));

        Player player = new Player();
        //playerrepository.save(player);
        //System.out.println("player added: " + player.getName());
        session.setAttribute("player", player);

        Player p = (Player)session.getAttribute("player");
        p.setNum(0);
        p.setNumCorrect(0);
        p.setNumWrong(0);
        p.clearAnswers();

        return "home";
    }

    @GetMapping("/start")
    public String start(HttpSession session) {

        Player player = new Player();
        session.setAttribute("player", player);

        Player p = (Player)session.getAttribute("player");
        p.setNum(0);
        p.setNumCorrect(0);
        p.setNumWrong(0);
        p.clearAnswers();

        return "start";
    }

    @GetMapping("/spel/{cat}")
    public String spel(Model model, HttpSession session, @PathVariable Integer cat) {
        Player player = (Player) session.getAttribute("player");
        player.increaseNum();
        session.setAttribute("numQ", player.getNum());
        session.setAttribute("catGlosa", cat);
        Glosa englosa = glosaService.getGlosaInCat(cat);
        model.addAttribute("glosa", englosa);
        session.setAttribute("enGlosa", englosa);
        return "spel";
    }



    @PostMapping("/spela")
    public String spela(Model model, HttpSession session, @RequestParam String answer) {

        Glosa englosa = (Glosa) session.getAttribute("enGlosa");

        Player p = (Player)session.getAttribute("player");


        if (glosaService.checkAnswer(englosa, answer)) {
            session.setAttribute("respons", "Rätt svar!!");
            p.increaseNumCorrect();

        } else {
            session.setAttribute("respons", "Fel svar.\n " + englosa.getSwe() + " = " + englosa.getEng() + ". Du svarade: " + answer + ".");
            p.increaseNumWrong();
            p.addToWrongAnswers(glosaService.wrongAnswer(englosa, answer));
        }

        model.addAttribute("lista", p.getAnswers());
        model.addAttribute("player", p);

        int num = (int) session.getAttribute("numQuestions");


        if (p.getNum() >= num) {
            session.setAttribute("respons", null);
            return "finish";
        } else {
            return "redirect:/spel/" + session.getAttribute("catGlosa");
        }


    }

    /* funkar inte längre efter ändringar i apiet till pathvariabel
        @GetMapping("/cat")
    //The @ResponseBody annotation tells a controller that the object returned is automatically serialized into JSON and passed back into the HttpResponse object.
    public @ResponseBody
    List<Glosa> cat(@RequestParam int num) {
        // This returns a JSON or XML with the users
        return (List<Glosa>) glosaService.getAllGlosorInCat(num); //http://localhost:8081/cat?num=3
    }
     */


}

//spring.datasource.url=jdbc:mysql://localhost:3306/engelska?useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
//spring.jpa.hibernate.ddl-auto=update