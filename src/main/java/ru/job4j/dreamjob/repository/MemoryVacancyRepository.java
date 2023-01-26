package ru.job4j.dreamjob.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.dreamjob.model.Vacancy;

import java.util.*;

import static java.time.LocalDateTime.now;

@Repository
public class MemoryVacancyRepository implements VacancyRepository {
    private int nextId = 1;
    private final Map<Integer, Vacancy> vacancies = new HashMap<>();

    public MemoryVacancyRepository() {
        save(new Vacancy(0, "Intern Java Developer",
                "открытая вакансия для прохождения стажировки", now()));
        save(new Vacancy(0, "Junior Java Developer",
                "открытая вакансия для джуна", now()));
        save(new Vacancy(0, "Junior+ Java Developer",
                "открытая вакансия для джуна с опытом", now()));
        save(new Vacancy(0, "Middle Java Developer",
                "открытая вакансия для мидла", now()));
        save(new Vacancy(0, "Middle+ Java Developer",
                "открытая вакансия для мидла от 2х лет опыта", now()));
        save(new Vacancy(0, "Senior Java Developer",
                "открытая вакансия для синьора", now()));
    }

    @Override
    public Vacancy save(Vacancy vacancy) {
        vacancy.setId(nextId++);
        vacancies.put(vacancy.getId(), vacancy);
        return vacancy;
    }

    @Override
    public boolean deleteById(int id) {
        return vacancies.remove(id) != null;
    }

    @Override
    public boolean update(Vacancy vacancy) {
        return vacancies.computeIfPresent(
                vacancy.getId(), (id, oldVacancy) -> new Vacancy(
                        id, vacancy.getTitle(), vacancy.getDescription(),
                        vacancy.getCreationDate())) != null;
    }

    @Override
    public Optional<Vacancy> findById(int id) {
        return Optional.ofNullable(vacancies.get(id));
    }

    @Override
    public Collection<Vacancy> findAll() {
        return vacancies.values();
    }
}
