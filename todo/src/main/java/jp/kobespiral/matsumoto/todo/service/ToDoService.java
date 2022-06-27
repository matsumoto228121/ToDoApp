package jp.kobespiral.matsumoto.todo.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.kobespiral.matsumoto.todo.dto.ToDoForm;
import jp.kobespiral.matsumoto.todo.entity.ToDo;
import jp.kobespiral.matsumoto.todo.exception.ToDoAppException;
import jp.kobespiral.matsumoto.todo.repository.ToDoRepository;

@Service
public class ToDoService {
    @Autowired
    ToDoRepository tRepo;

    /**
     * ToDoを作成する
     * @param mid
     * @param form
     * @return
     */
    public ToDo createToDo(String mid, ToDoForm form){
        ToDo t = form.toEntity();
        t.setMid(mid);
        t.setDone(false);
        t.setCreatedAt(new Date());
        return tRepo.save(t);
    }

    /**
     * 番号を指定してToDoを取得
     * @param seq
     * @return
     */
    public ToDo getToDo(Long seq){
        ToDo t = tRepo.findById(seq).orElseThrow(
               () -> new ToDoAppException(ToDoAppException.NO_SUCH_TODO_EXISTS, seq + ": No such ToDo exists"));
       return t;
    }
    
    public List<ToDo> getToDoList(String mid){
        return tRepo.findByMidAndDone(mid, false);
    }
    
    public List<ToDo> getDoneList(String mid){
        return tRepo.findByMidAndDone(mid, true);
    
    }
    
    public List<ToDo> getToDoList(){
        return tRepo.findByDone(false);
        
    }
    
    public List<ToDo> getDoneList(){
        return tRepo.findByDone(true);
    }
}