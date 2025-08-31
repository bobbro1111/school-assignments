'''
A Genetic Algorithm is a search technique for solving a problem.
It explores a solution space by creating a population of "solutions".
It then calculates the fitness of each solution and uses that fitness
to create the next generation.

The more fit an individual is, the more likely they will be selected as a
parent for the next generation. To make a new individual for the next
generation, 2 people are chosen at random. Those with a higher fitness score
are more likely to be selected. The solutions are recombined to make 2
new individuals. On occasion, one of the individuals is subject to a "mutation" --
a random modification to the individual solution.

In this problem, which is a classic scheduling problem,
the goal is to enroll each student in 1 course.
Ideally, each student would be enrolled in their preferred course (ie. the one
ranked the highest), and every course would have the same number of students.

There is a list of students.
An "individual" is a list of courses from "A" to "E" the length of students.
Each course in an individual/solution corresponds to a student.

So ... individual[0]="B" means students[0] is enrolled in course "B"
students[0].get_preference("B") would tell you how the student ranks that course.
The higher the rank, the more they like it.
'''

import random
from fitness import fitness_function
from nextgen import next_gen

# number of individual solutions at each iteration
population_size = 100

# number of times the population will evolve
generations = 500

# probability of a mutation occuring
mutation_rate = 0.20


def solve(students,courses,fitness_function):
    '''
    Use a genetic algorithm to find the best scheduling of students in courses.
    @param students: list of Student objects
    @parm courses: list of courses students can enroll in.
    '''

    student_count = len(students)

    # ideally, every class would have the same number of students enrolled
    ideal_enrollment = student_count // len(courses)

    # perfect score would be each student gets their highest ranked course
    # AND there is the same number of students in every course
    # If there is equal enrollment, the score will be based on the ranking.
    # Any unevenness in enrollment will result in a lower score
    perfect_score = len(students)*len(courses)

    # generate an initial population
    population = []
    for _ in range(population_size):
        # randomly assign each student to a course
        # this is an individual/solution. Add it to the population
        population.append([ random.choice(courses) for i in range(student_count)])

    # track the best individual throughout the process
    best_fitness = None
    best_schedule = None

    #fit_data holds tracked info
    fit_data = []
    for i in range(generations):
        if (i%500==0):
            # letting the user know how far along it is in evolution
            print(f'generation {i}')

        population_fitness = fitness_function(population, students, courses)

        #Sources Used -Bobby
        #https://docs.python.org/3/library/functions.html
        #https://www.askpython.com/python/array/append-an-array-in-python
        #fitdata = ((GENERAION NUM, MAX OF GENERAION, AVG OF GENERATION))
        fit_data.append((i, max(population_fitness), sum(population_fitness) / len(population_fitness)))
        
        # determine most fit of this population.
        # determine if better than any previous generation
        best_in_pop = max(population_fitness)

        # if this is our first generation ...
        if best_fitness==None:
            print(f'initial best fitness: {best_in_pop}')

        # if this generation is better than any in the past ...
        if best_fitness==None or best_in_pop > best_fitness:
            print(f'improved to {best_in_pop}')
            best_fitness = best_in_pop
            index_of_best = population_fitness.index(best_in_pop)
            best_schedule = list(population[index_of_best])

        # always include the best of the best when creating the next gen
        population.insert(0,best_schedule)
        population_fitness.insert(0,best_fitness)

        # create next generation
        population = next_gen(population,population_fitness,population_size)

        # maybe a mutation
        if random.random() < mutation_rate:
            rand_student = random.randrange(0,len(students))
            rand_individual = random.randrange(0,len(population))
            random_value = random.choice(courses)
            population[rand_individual][rand_student] = random_value

    print('\n\n BEST ...')
    print(best_schedule)
    print(f'Fitness of this is {best_fitness}')
    print(f'Ideal fitness is {perfect_score}')

    # print the solution
    print_roster(best_schedule,students,courses)
    #print_fit(fit_data)

def print_roster(schedule,students,courses):
    # create a dictionary to hold the list of students enrolled in each course
    rosters = {chr(x):[] for x in range(ord("A"), ord("A") + len(courses))}

    for i in range(len(schedule)):
        # add student[i] to the roster which they were assigned.
        # note that the position [i] in the schedule corresponds to the students[i]
        rosters[schedule[i]].append(students[i])

    # print the roster
    for k,v in rosters.items():
        print(f'\n___Course {k}___')
        for s in v:
            print(s)

#Call this in solver if you want to see all 500 recordings
def print_fit(fit_data):
    for i in range(len(fit_data)):
        print(fit_data)
    
    



