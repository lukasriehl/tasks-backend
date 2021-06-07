package br.ce.wcaquino.taskbackend.controller;

/*
 * @criado em 06/06/2021 - 12:38
 * @autor Lukas Riehl Figueiredo
 */

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

public class TaskControllerTest {

    @Mock
    private TaskRepo taskRepo;

    //MOCKS CRIADOS NESTE CONTEXTO SERÁ INJETADOS NA CLASSE COM ESSA ANOTAÇÃO
    @InjectMocks
    private TaskController controller;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void naoDeveSalvarTarefaSemDescricao() {
        Task todo = new Task();

        //todo.setTask("Descricao");
        todo.setDueDate(LocalDate.now());

        try {
            controller.save(todo);

            Assert.fail("Não deveria chegar nesse ponto");
        } catch (ValidationException ex) {
            Assert.assertEquals("Fill the task description", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaSemData() {
        Task todo = new Task();

        todo.setTask("Descricao");

        try {
            controller.save(todo);

            Assert.fail("Não deveria chegar nesse ponto");
        } catch (ValidationException ex) {
            Assert.assertEquals("Fill the due date", ex.getMessage());
        }
    }

    @Test
    public void naoDeveSalvarTarefaComDataPassada() {
        Task todo = new Task();

        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.of(2010, 1, 1));

        try {
            controller.save(todo);

            Assert.fail("Não deveria chegar nesse ponto");
        } catch (ValidationException ex) {
            Assert.assertEquals("Due date must not be in past", ex.getMessage());
        }
    }

    @Test
    public void deveSalvarTarefaComSucesso() throws ValidationException {
        Task todo = new Task();

        todo.setTask("Descricao");
        todo.setDueDate(LocalDate.now());

        controller.save(todo);

        //Verifica se o taskRepo foi invocada no método salvar, enviado a task como parâmetro
        Mockito.verify(taskRepo).save(todo);
    }
}
