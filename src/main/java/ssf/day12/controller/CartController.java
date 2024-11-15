package ssf.day12.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ssf.day12.models.Constants;
import ssf.day12.models.LineItem;

@Controller
@RequestMapping("/cart")
public class CartController {

    // GET /cart
    @GetMapping
    public String getCart(
            @RequestParam(required = false) String name,
            // @RequestParam(defaultValue = "4") int count,
            @RequestParam(required = false) String count,
            Model model) {

        int itemCount = 3;
        try {
            itemCount = Integer.parseInt(count);
        } catch (Exception ex) {
        }

        List<LineItem> items = Constants.generateLineItems(itemCount);

        model.addAttribute("name", name.toUpperCase());
        model.addAttribute("items", items);
        model.addAttribute("count", 9);

        return "cart";
    }

    @GetMapping("/map")
    public String getCart(
            @RequestParam MultiValueMap<String, String> form,
            Model model) {

        String name = form.getFirst("name");
        String count = form.getFirst("count");

        System.out.printf(">>>>>   get first: %s\n", form.getFirst("delivery"));
        System.out.printf(">>>>>   get: %s\n", form.get("delivery"));

        int itemCount = 3;
        try {
            itemCount = Integer.parseInt(count);
        } catch (Exception ex) {
        }

        List<LineItem> items = Constants.generateLineItems(itemCount);

        model.addAttribute("name", name.toUpperCase());
        model.addAttribute("items", items);
        model.addAttribute("count", 9);

        return "cart";
    }
}