
def fitness_function(population, students, courses):
    
    student_count = len(students)
    ideal_enrollment = student_count // len(courses)
    population_fitness = []

    for individual in population:
        fitness = 0

        for j, course in enumerate(individual):
            fitness += students[j].get_preference(course)

        for c in courses:
            fitness += min(0, ideal_enrollment - individual.count(c))

        population_fitness.append(fitness)

    return population_fitness
