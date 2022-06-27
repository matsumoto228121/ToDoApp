package jp.kobespiral.matsumoto.todo.controller;

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
    String Login(Model model){
        LoginForm loginform = new LoginForm();
        model.addAttribute("LoginForm", loginform);
        return "index";
    }


    @GetMapping("/{mid}/todos")
    String showList(@PathVariable String mid, @ModelAttribute(name = "LoginForm") LoginForm loginform, Model model){
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
        List<ToDo> todolist = tService.getToDoList(mid);
        model.addAttribute("todolist", todolist);
        List<ToDo> donelist = tService.getDoneList(mid);
        model.addAttribute("donelist", donelist);
        return "list";
    }

    @GetMapping("/{mid}/todos/{seq}/done")
    String doneToDo(@PathVariable String mid, @PathVariable Long seq, Model model){
        tService.getToDo(seq).setDone(true);
        List<ToDo> todolist = tService.getToDoList(mid);
        model.addAttribute("todolist", todolist);
        List<ToDo> donelist = tService.getDoneList(mid);
        model.addAttribute("donelist", donelist);
        return "list";
    }
}
