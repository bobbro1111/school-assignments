def alt_fitness_function(population, students, courses):
    
    total_students = len(students)
    ideal_size = total_students // len(courses)
    fitness_scores = []

    for schedule in population:
        score = 0
        course_counts = {course: 0 for course in courses}

        # Calculate fitness score
        for i, course in enumerate(schedule):
            score += students[i].get_preference(course)
            course_counts[course] += 1

        for count in course_counts.values():
            score -= abs(count - ideal_size)

        fitness_scores.append(score)

    return fitness_scores