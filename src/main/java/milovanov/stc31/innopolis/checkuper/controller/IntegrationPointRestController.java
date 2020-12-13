package milovanov.stc31.innopolis.checkuper.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Контроллер для реализации точки взаимодействия, например, с мобильным клиентом.
 * Получает список заявок и возвращает результаты выполнения
 */

@RestController
@RequestMapping("/integration")
public class IntegrationPointRestController {

    /**
     * Получение списка заявок для выполнения, например, из мобильного клиента
     */
    @GetMapping("{executor}")
    public List<Object> getAllRequest(@PathVariable String executorId) {
        // TODO: List<Object> нужно заменить на List<Interface>, Interface - интерфейс заявки на помощь
        List<Object> list = new ArrayList<>();
        // TODO: Написать реализацию
        return list;
    }

    /**
     * POST запрос на добавление результатов выполнения заявки
     * @param object
     * @return
     */
    @PostMapping
    public List<Object> TaskResult(@RequestBody Object object) {
        // TODO: List<Object> нужно заменить на List<Interface>, Interface - интерфейс результатов выполнения заявки
        // TODO: Написать реализацию
        return null;
    }
}
