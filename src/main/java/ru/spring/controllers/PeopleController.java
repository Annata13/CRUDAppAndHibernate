package ru.spring.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.spring.dao.PersenDAO;
import ru.spring.models.Person;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PersenDAO persenDAO;

    @Autowired
    public PeopleController(PersenDAO persenDAO) {
        this.persenDAO = persenDAO;
    }

    //возвращает список из людей
    @GetMapping()
    public String index(Model model) {
        //получим всех людей из DAO и передадим на отображение и представление
        model.addAttribute("people", persenDAO.index());
        return "people/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id,
                       Model model) {
        //получим одного чела по его id из  DAO  и передадим на отображение  в представление
        model.addAttribute("person", persenDAO.show(id));
        return "/people/show";
    }


    /*  @GetMapping("/new")
      public String newPerson(Model model){ //если используем таимлиф формы -передаем обект для которого эта форма нужна
          model.addAttribute("person", new Person());
          return "people/new";
      }*/
    //равнозначно
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/new";
    }

    //создание человека
    @PostMapping()
    public String create(@ModelAttribute("person") @Valid Person person, //получаем данные из формы // @Valid - добавили валидацию
                         BindingResult bindingResult) { // @Valid -если в нем ошибка она помещается в BindingResult bindingResult
        if (bindingResult.hasErrors()){
            return "people/new";
        }
        persenDAO.save(person); //добавляем в список
        return "redirect:/people"; //редирект
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("person", persenDAO.show(id));
        return "people/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors()){
            return "people/edit";
        }
        persenDAO.update(id, person);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        persenDAO.delete(id);
        return "redirect:/people";
    }
}
