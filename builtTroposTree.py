from bs4 import BeautifulSoup
import re
#soup = BeautifulSoup(open("sample_1.tropos"), 'xml')
#soup = BeautifulSoup(open("sample_2.xml"), 'xml')
soup = BeautifulSoup(open("sample_3.tropos"), 'xml')


sequential_and = "{0} * {1}"
sequential_or = "MAX({0},{1})"
parallel_and = "{0} * {1}"
parallel_or = "MAX({0},{1})"
optional = "{0}"
alternative = "{0}"
try_template = "{0} * {1} - {0} * {2} + {2}"
cardinality = "({0})^{1}"
retry = "1 - (1 - {0})^({1}+1)"

pattern = r'\[(.+)\]'
#incoming_regex = r'incomingRelations=\"([^"]+)\"'
incoming_regex = r'incomingRelations="([^"]*)"'
outcoming_regex = r'outcomingRelations="([^"]*)"'
clean = r'[\'(.*)\']'


arvore = soup.find_all('TroposClasses')
relacoes = soup.find_all('TroposRelations')

#print(soup.prettify())
#tag = soup.TroposClasses
#print(tag)


def whatRelation(my_relation):
    if my_relation['xsi:type'] == "it.itc.sra.taom4e.model.core.informalcore:MeansEnd":
        return 'implication'
    if my_relation['xsi:type'] == "it.itc.sra.taom4e.model.core.informalcore:BooleanDecomposition":
        return 'and'
    if my_relation['xsi:type'] == "it.itc.sra.taom4e.model.core.informalcore:BooleanDecLink":
        return 'or'

    return 'idk'

def getStep(goal, lastGoal=None):
    print("\n------------------------------OBJETIVO------------------------------\n")
    if goal == None:
        return
    print(goal["name"])
    print("\n--------------------------------------------------------------------\n")


    outcoming_relations = re.findall(outcoming_regex,str(goal))
    incoming_relations = re.findall(incoming_regex,str(goal))

    out_list = []
    in_list = []

    if outcoming_relations:
        out_list = outcoming_relations[0].split()
    if incoming_relations:
        in_list = incoming_relations[0].split()

    for relacao in relacoes:
        for outcoming_relation in out_list:
            print("TESTE OUT:"+ outcoming_relation)
            if relacao['xmi:id'] == outcoming_relation:
                print("\n------------------------------RELACAO------------------------------\n")
                print("Outcoming relations:")
                print(relacao)
                try:
                    if relacao["targets"]:
                        print(relacao["targets"])
                        next_goal = nextGoal_or_Task(relacao["targets"])
                        if next_goal != lastGoal:
                            print(next_goal["name"])
                            getStep(next_goal,goal)

                except KeyError:
                     print(relacao["outcomingRelations"])
                     if relacao["outcomingRelations"]:
                         for out_list_inside in relacao["outcomingRelations"].split():
                             print(out_list_inside)
                             next_goal = nextGoal_or_Task_by_incoming(out_list_inside)
                             if next_goal != lastGoal:
                                 #print(next_goal["name"])
                                 getStep(next_goal,goal)

        for incoming_relation in in_list:
            print("TESTE IN:"+ incoming_relation)
            if relacao['xmi:id'] == incoming_relation:
                print("\n------------------------------RELACAO------------------------------\n")
                print("Incoming relations:")
                print(relacao)
                if relacao["sources"]:
                    print(relacao["sources"])
                    try:
                        next_goal = nextGoal_or_Task(relacao["sources"])
                        if next_goal != lastGoal:
                            #print(next_goal["name"])
                            getStep(next_goal,goal)
                    except KeyError:
                        pass

def nextGoal_or_Task(id):
    for element in arvore:
        if element['xmi:id'] == id:
            return element

def nextGoal_or_Task_by_incoming(id):
    for element in arvore:
        try:
            if element['incomingRelations'] == id:
                return element
        except KeyError:
                pass




actor = soup.find('TroposClasses', isSystem='true')
actor_id = actor['xmi:id']

goals = []
tasks = []

unidentified_element = soup.find_all(Actor=actor_id)
for element in unidentified_element:
    if element['xsi:type'] == 'it.itc.sra.taom4e.model.core.informalcore.formalcore:FHardGoal':
        goals.append(element)
    if element['xsi:type'] == 'it.itc.sra.taom4e.model.core.informalcore.formalcore:FPlan':
        tasks.append(element)


print("\n")
print("Objetivos:\n")
i = 1
for x in goals:
    print(str(i) + ':' + str(x) + '\n')
    i+=1

#print(goals)
print("\n")
print("Tasks:\n")
i = 1
for x in tasks:
    print(str(i) + ':' + str(x) + '\n')
    i+=1

#print(tasks)
print("\n")
print("Relacoes:\n")
#print(relacoes)
i = 1
for x in relacoes:
    print(str(i) + ':' + str(x) + '\n')
    i+=1

print("\n")
print("-------------------------------------------------------------------------")
print("\n")

#startVist(goals, soup)

for goal in goals:
    next_step = getStep(goal)
    if next_step == 'None':
        print('acabou\n')
    #next_visit = my_soup.find(attrs={"outcomingRelations":re.compile(goal['incomingRelations'])})
