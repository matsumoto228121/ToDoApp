package jp.kobespiral.matsumoto.todo.controller;

import java.util.Date;
import java.util.List;

import org.hibernate.engine.jdbc.spi.ResultSetReturn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.kobespiral.matsumoto.todo.dto.LoginForm;
import jp.kobespiral.matsumoto.todo.dto.ToDoForm;
import jp.kobespiral.matsumoto.todo.entity.ToDo;
import jp.kobespiral.matsumoto.todo.service.ToDoService;

@Controller
@RequestMapping("/")
public class ToDoController {
    @Autowired
    ToDoService tService;

    @GetMapping("")
    String showLogin(Model model){
        LoginForm loginform = new LoginForm();
        model.addAttribute("LoginForm", loginform);
        return "index";
    }

    @GetMapping("/login")
    String login(LoginForm form, Model model){
        String mid = form.getMid();
        return "redirect:/" + mid + "/todos";
    }

    @GetMapping("/{mid}/todos")
    String showList(@PathVariable String mid, Model model){
        ToDoForm todoform = new ToDoForm();
        model.addAttribute("ToDoForm", todoform);
        List<ToDo> todolist = tService.getToDoList(mid);
        model.addAttribute("todolist", todolist);
        List<ToDo> donelist = tService.getDoneList(mid);
        model.addAttribute("donelist", donelist);

        return "list";
    }

    @PostMapping("/{mid}/todos/register")
    String showNewList(@PathVariable String mid , @ModelAttribute(name = "ToDoForm") ToDoForm todoform, Model model){
        tService.createToDo(mid, todoform);
        return "redirect:/" + mid + "/todos";
    }

    @GetMapping("/{mid}/todos/{seq}/done")
    String doneToDo(@PathVariable String mid, @PathVariable Long seq, @ModelAttribute(name="ToDoForm") ToDoForm todoform, Model model){
        ToDo t = tService.getToDo(seq);
        t.setDone(true);
        t.setDoneAt(new Date());
        tService.updateToDo(t);
        return "redirect:/" + mid + "/todos";
    }

    @GetMapping("/{mid}/todos/{seq}/not_done")
    String notDoneToDo(@PathVariable String mid, @PathVariable Long seq, @ModelAttribute(name="ToDoForm") ToDoForm todoform, Model model){
        ToDo t = tService.getToDo(seq);
        t.setDone(false);
        tService.updateToDo(t);

        return "redirect:/" + mid + "/todos";
    }

    @GetMapping("/{mid}/all/todos")
    String allToDo(@PathVariable String mid, Model model){
        model.addAttribute("mid", mid);
        List<ToDo> todolist = tService.getToDoList();
        model.addAttribute("todolist", todolist);
        List<ToDo> donelist = tService.getDoneList();
        model.addAttribute("donelist", donelist);
        return "alllist";
    }
}
