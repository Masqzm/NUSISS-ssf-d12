package ssf.day12.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

// Static import
import static ssf.day12.models.GenerateConstants.*;

@Controller
@RequestMapping("/generate")
public class GenerateController {
    @Autowired
    private ResourceLoader resourceLoader;

    // GET /generate w/o list query (/generate?name=fred&count=4)
    @GetMapping
    public String getNumber(
            @RequestParam String name,
            @RequestParam int count,
            Model model) throws IOException {

        // Clamp count
        if (count < 1)
            count = 1;
        else if (count > 31)
            count = 31;

        // Generate list of num. filenames (string)
        List<String> numFileList = generateNumFilenames(count);

        model.addAttribute("name", name);
        model.addAttribute("count", count);
        model.addAttribute("numFileList", numFileList);

        return "generate";
    }

    // Fn to generate number img filenames
    private List<String> generateNumFilenames(int count) throws IOException {
        // Get num images dir
        Resource imgResource = resourceLoader.getResource("classpath:static/numbers");
        File imagesDir = Path.of(imgResource.getURI()).toFile();

        // Get all files (names) in num images dir and store into list
        List<File> listOfFiles = new ArrayList<>();

        for (File file : imagesDir.listFiles())
            listOfFiles.add(file);

        Collections.shuffle(listOfFiles);

        // Get total files as per how many was requested (count)
        List<String> generatedList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            if (!listOfFiles.isEmpty()) {
                // Take first from list & remove it
                String filename = listOfFiles.removeLast().getName();

                String prefix = "numbers/"; // dir where img is from

                generatedList.add(prefix + filename);
            }
        }

        return generatedList;
    }

    // GET /generate/samplesoln?name=fred&count=4
    @GetMapping("/samplesoln")
    public String getGenerate(@RequestParam MultiValueMap<String, String> form, Model model) {

        String name = form.getFirst(ATTR_NAME);
        int count = 0;
        List<String> values;

        // Check if we have list query string
        if (form.containsKey(ATTR_LIST)) {
            // convert the CSV -> List<int> -> List<string>
            values = toList(form.getFirst(ATTR_LIST))
                    .stream()
                    .map(val -> "/numbers/number%d.jpg".formatted(val))
                    .toList();

            model.addAttribute(ATTR_VALUES, values);

            // We don't have the name query string, then just display the list of numbers
            if (!form.containsKey(ATTR_NAME))
                return "generate_list";

            // Set the number count of the list
            count = values.size();

        } else {

            // Process as before
            count = toInt(form.getFirst(ATTR_COUNT), 1);
            // int[ 0, 5, 2 ] -> String['/numbers/number0.jpg', '/numbers/number5.jpg',
            // '/numbers/number2.jpg' ]
            values = generateRandom(count).stream()
                    .map(val -> "/numbers/number%d.jpg".formatted(val))
                    .toList();
        }

        model.addAttribute(ATTR_NAME, name);
        model.addAttribute(ATTR_COUNT, count);
        model.addAttribute(ATTR_VALUES, values);

        return "generate";
    }
}
