import networkx as nx #libreria para grafos
import tkinter as tk
from tkinter import *

#librería para ventanas emergentes
from tkinter import messagebox
#-------------Interfaz---------------------------------------------
#Declaracion de la interfaz y variables para guardar destinos
root = tk.Tk()

primera = tk.StringVar(root)
fin = tk.StringVar(root)

entrada1 = tk.StringVar(root)
entrada2 = tk.StringVar(root)

resultado = tk.StringVar(root)

#Definicion de las caracteristicas de la interfaz
root.geometry("1200x800")
root.configure(background="light blue")
tk.Wm.wm_title(root, "Metro Atenas")

#Imagen del metro
miImagen=PhotoImage(file="mapaMetro.gif")    
photo = Label(root, image=miImagen).place(x=150,y=0)





#declaracion del boton para mostrar las paradas 
#tengo que añadir funcionalidad para que calcule la ruta
boton = Button(
    root,
    text = "Calcular",
    font=("courier", 14),
    bg = "#00a8e8",
    fg="white",
    command= lambda: calcular()
)

#declaracion de todas las cadenas de texto
intro = Label(
    root,
    text= "Bienvenido a la app Metro Atenas\n Porfavor indique lugar de partida y lugar de destino",
    bg="AntiqueWhite1",
    font= ("Gadugi", 15)
)
#añadimos la variable texto para poder ser modificada y declaramos labels para direcciones
texto = " Aghia Paraskevi, Aghios Antonios, Aghios Dimitrios  \n Aghios Eleftherios, Aghios Ioannis, Aghios Nkolaos \n Airport , Akropoli, Ambelokipi, Ano Patissia \n Attiki, Dafni \n Doukissis Plakentias, Egaleo \n Eleonas,Ethniki, Evangelismos \n Faliro, Halandri, Holargos \n Iraklio, Irini, KAT \n Kallithea, Katehaki, Kato Patissia \n Kerameikos, Kifissia, Koropi \n Larissa Station, Maroussi, Megaro Moussikis \n Metaxourghio, Monastiraki,\n Moschato, Nea Ionia, Neos Kosmos \n Neratziotissa, Nomismatokopio, Omonia, \n Paiania-Kantza, Pallini \n Panepistimio, Panormou, Pefkakia \n Perissos, Petralona, Piraeus \n Sepolia, Sygrou-Fix, Syntagma ,\n Tavros, Thissio \n Victoria"
tituloDirecciones = Label(
    root,
    text = "Listado de paradas:",
    bg="AntiqueWhite1",
    font= ("Gadugi", 15)
)
direciones = Label(
    root,
    text = texto,
    bg="AntiqueWhite1",
    justify="left",
    font= ("Gadugi", 10)
)
#Declaracion de labels relacionadas con parada de inicio y fin
inicio = Label(
    root,
    bg="AntiqueWhite1",
    text= "Direccion de partida",
    font= ("Gadugi", 10)
)
confInicio = Label(
    root,
    bg="AntiqueWhite1",
    textvariable=primera,
    font= ("Gadugi", 10)
)
confDestino = Label(
    root,
    bg="AntiqueWhite1",
    textvariable=fin,
    font= ("Gadugi", 10)
)
destino = Label(
    root,
    bg="AntiqueWhite1",
    text= "Direccion destino",
    font= ("Gadugi", 10)
)   
#Declaracion labels para la ruta
introRuta = Label(
    root,
    text= "Ruta:",
    bg="AntiqueWhite1",
    font= ("Gadugi", 12)
)
ruta = Label(
    root,
    bg="AntiqueWhite1",
    textvariable= resultado,
    font= ("Gadugi", 10)
)#Falta añadir la variable donde se guardara la ruta en text variable

#Declaracion de las dos cajas input
Entry1 = Entry(root,
    fg="black",
    bg="white",
    textvariable= entrada1)

Entry2 = Entry(root,
    fg="black",
    bg="white",
textvariable= entrada2)

#Ubicacion de la intro
intro.place(x = 220, y = 600)

#Ubicacion de los label
inicio.place(x= 200, y= 660)
destino.place(x = 600, y= 660 )

confInicio.place(x=200, y = 725)
confDestino.place(x = 600, y = 725)

direciones.place(x = 830, y = 100)
tituloDirecciones.place(x=830, y = 50)

introRuta.place(x = 830, y= 430)
ruta.place(x = 830, y= 460)

#Ubicacion de los entry
Entry1.place(x= 200, y = 700)
Entry2.place(x= 600, y = 700)

#ubicacion del boton
boton.place(x = 400, y = 725)




#------------- FIN Interfaz---------------------------------------------
########################################################################################################################################################################
#PARADAS DE CADA LINEA
linea3=['Egaleo','Eleonas','Kerameikos','Monastiraki','Syntagma','Evangelismos','Megaro Moussikis','Ambelokipi','Panormou','Katehaki','Ethniki','Holargos','Nomismatokopio','Aghia Paraskevi','Halandri','Doukissis Plakentias','Pallini','Paiania-Kantza','Koropi','Airport']
linea2=['Aghios Antonios','Sepolia','Attiki','Larissa Station','Metaxourghio','Omonia','Panepistimio','Syntagma','Akropoli','Sygrou-Fix','Neos Kosmos','Aghios Ioannis','Dafni','Aghios Dimitrios']
linea1=['Piraeus','Faliro','Moschato','Kallithea','Tavros','Petralona','Thissio','Monastiraki','Omonia','Victoria','Attiki','Aghios Nkolaos','Kato Patissia','Aghios Eleftherios','Ano Patissia','Perissos','Pefkakia','Nea Ionia','Iraklio','Irini','Neratziotissa','Maroussi','KAT','Kifissia']
todaslineas=[linea1,linea2,linea3]
########################################################################################################################################################################
#INICIALIZACION DEL GRAFO DE PARADAS
grafo = nx.Graph()
#ESTACIONES
contador=1

#LINEA 3
for i in linea3:
    if i=='Monastiraki':
        grafo.add_node(i,lineas=[100,0,100,0])
    elif i=='Syntagma':
        grafo.add_node(i,lineas=[0,100,100,0])
    else:
        grafo.add_node(i,lineas=[0,0,100,contador])
    contador+=1
#LINEA 2
contador=1
for i in linea2:
    if i=='Omonia':
        grafo.add_node(i,lineas=[100,100,0,0])
    elif i=='Syntagma':
        pass
    elif i=='Attiki':
        grafo.add_node(i,lineas=[100,100,0,0])
    else:
        grafo.add_node(i,lineas=[0,100,0,contador])
    contador+=1
#LINEA 1
contador=1
for i in linea1:
    if i=='Monastiraki':
        pass
    elif i=='Omonia':
        pass
    elif i=='Attiki':
        pass
    else:
        grafo.add_node(i,lineas=[100,0,0,contador])
    contador+=1

####################################################################################################################################################################3
#TIEMPO DE TRAYECTOS ENTRE ESTACIONES
#linea3
grafo.add_edge('Egaleo','Eleonas', peso = 1)  
grafo.add_edge('Eleonas','Kerameikos', peso = 2)
grafo.add_edge('Kerameikos','Monastiraki', peso = 2)
grafo.add_edge('Monastiraki','Syntagma', peso = 1)
grafo.add_edge('Syntagma','Evangelismos', peso = 1)
grafo.add_edge('Evangelismos','Megaro Moussikis', peso = 1)
grafo.add_edge('Megaro Moussikis','Ambelokipi', peso = 2)
grafo.add_edge('Ambelokipi','Panormou', peso = 2)
grafo.add_edge('Panormou','Katehaki', peso = 3)
grafo.add_edge('Katehaki','Ethniki', peso = 1)
grafo.add_edge('Ethniki','Holargos', peso = 1)
grafo.add_edge('Holargos','Nomismatokopio', peso = 2)
grafo.add_edge('Nomismatokopio','Aghia Paraskevi', peso = 1)
grafo.add_edge('Aghia Paraskevi','Halandri', peso = 1)
grafo.add_edge('Halandri','Doukissis Plakentias', peso = 8)
grafo.add_edge('Doukissis Plakentias','Pallini', peso = 2)
grafo.add_edge('Pallini','Paiania-Kantza', peso = 5)
grafo.add_edge('Paiania-Kantza','Koropi', peso = 6)
grafo.add_edge('Koropi','Airport', peso = 5)
#linea2
grafo.add_edge('Aghios Antonios','Sepolia', peso = 1)
grafo.add_edge('Sepolia','Attiki', peso = 1)
grafo.add_edge('Attiki','Larissa Station', peso = 2)
grafo.add_edge('Larissa Station','Metaxourghio', peso = 1)
grafo.add_edge('Metaxourghio','Omonia', peso = 1)
grafo.add_edge('Omonia','Panepistimio', peso = 1)
grafo.add_edge('Panepistimio','Syntagma', peso = 1)
grafo.add_edge('Syntagma','Akropoli', peso = 1)
grafo.add_edge('Akropoli','Sygrou-Fix', peso = 1)
grafo.add_edge('Sygrou-Fix','Neos Kosmos', peso = 1)
grafo.add_edge('Neos Kosmos','Aghios Ioannis', peso = 1)
grafo.add_edge('Aghios Ioannis','Dafni', peso = 2)
grafo.add_edge('Dafni','Aghios Dimitrios', peso = 1)
#linea1
grafo.add_edge('Piraeus','Faliro', peso = 4)
grafo.add_edge('Faliro','Moschato', peso = 3)
grafo.add_edge('Moschato','Kallithea', peso = 2)
grafo.add_edge('Kallithea','Tavros', peso = 1)
grafo.add_edge('Tavros','Petralona', peso = 2)
grafo.add_edge('Petralona','Thissio', peso = 2)
grafo.add_edge('Thissio','Monastiraki', peso = 1)
grafo.add_edge('Monastiraki','Omonia', peso = 2)
grafo.add_edge('Omonia','Victoria', peso = 1)
grafo.add_edge('Victoria','Attiki', peso = 3)
grafo.add_edge('Attiki','Aghios Nkolaos', peso = 1)
grafo.add_edge('Aghios Nkolaos','Kato Patissia', peso = 1)
grafo.add_edge('Kato Patissia','Aghios Eleftherios', peso = 2)
grafo.add_edge('Aghios Eleftherios','Ano Patissia', peso = 5)
grafo.add_edge('Ano Patissia','Perissos', peso = 2)
grafo.add_edge('Perissos','Pefkakia', peso = 2)
grafo.add_edge('Pefkakia','Nea Ionia', peso = 1)
grafo.add_edge('Nea Ionia','Iraklio', peso = 3)
grafo.add_edge('Iraklio','Irini', peso = 2)
grafo.add_edge('Irini','Neratziotissa', peso = 2)
grafo.add_edge('Neratziotissa','Maroussi', peso = 2)
grafo.add_edge('Maroussi','KAT', peso = 2)
grafo.add_edge('KAT','Kifissia', peso = 2)

def heuristica(paradaA,paradaB,A,B):
    cont=0
    for i in paradaA:
        if cont==3:
            break
        if i ==100 and paradaB[cont]==100:
            break
        cont+=1
    i=0
    for i in todaslineas[cont]:
        if i==A:#la funcion euristica priorizara los indices altos
            return True
        elif i==B:#la funcion euristica priorizara los indices bajos
            return False
def compartenlinea(paradaA,paradaB):
    flag=False
    cont=0
    for i in paradaA:
        
        if i ==100 and paradaB[cont]==100:
            flag=True
            break
        cont+=1
    return flag
def astar(inicio,fin):
    #comprobacion de pertenencia a una misma linea
    l_ini=grafo._node[inicio]['lineas']
    l_fin=grafo._node[fin]['lineas']
    campo1={}
    campo2={}
    campo3={}
    visitados={}
    actual=None
    #flag de la funcion euristica
    flagh=False
    flag=compartenlinea(l_ini,l_fin)
    if flag:
        flagh=heuristica(l_ini,l_fin,inicio,fin)
        actual=inicio
    else:
        l_i=grafo._node[inicio]['lineas']
        if compartenlinea([100,0,0,0],l_fin):
            if l_i[1]==100:
                campo1=astar(inicio,'Attiki')
                campo2=astar(inicio,'Omonia')
               
            if l_i[2]==100:    
                campo3=astar(inicio,'Monastiraki')
            
        
        elif compartenlinea([0,100,0,0],l_fin):
            if l_i[0]==100:
                campo1=astar(inicio,'Omonia')
                campo2=astar(inicio,'Attiki')
                if campo1.__len__()>campo2.__len__():
                    campo1=campo2
                    campo2={}
                else:
                    campo2={}
            
            if l_i[2]==100:
                campo2=astar(inicio,'Syntagma')
                if campo1.__len__()>campo2.__len__():
                    campo1=campo2
                    campo2={}
                else:
                    campo2={}      
        
        elif compartenlinea([0,0,100,0],l_fin):
            if l_i[0]==100:
                campo1=astar(inicio,'Monastiraki')
            if l_i[1]==100:
                campo1=astar(inicio,'Syntagma')
        for i in campo1:
            
            actual=i
    
    funcionf=10000
    funcionh=0
    funciong=0
    pendiente={}
   
    visitados=campo1
    visitados[fin]=0
    
    next=None
   
    anterior=None
    
    
    while visitados[fin]==0:
        funcionf=10000#reset de la funcionf
        for i in grafo[actual]:
            if i==anterior:
                pass
            else:
                funciong+=grafo[actual][i]['peso']
            
                if compartenlinea(grafo._node[i]['lineas'],grafo._node[fin]['lineas']):
                    l_i=grafo._node[i]['lineas']
                    flagh=heuristica(l_i,l_fin,i,fin)
                    if flagh:
                        funcionh=0-grafo._node[i]['lineas'][3]-100
                    else:
                        funcionh=grafo._node[i]['lineas'][3]
                   
                    if (funciong+funcionh)<funcionf:#si tiene funcion f menor pasara a ser el candidato a siguiente nodo
                        
                        funcionf=funciong+funcionh
                        if not next.__eq__(None):
                            pendiente[next]=funciong
                        funcionf=grafo[actual][i]['peso']
                        next=i
                    else:
                        if not next.__eq__(None):
                            pendiente[next]=funciong
                
        for i in pendiente:
            if pendiente[i]<funciong:
                pendiente[next]=funciong
                funciong=pendiente[i]
                next=i
        visitados[actual]=funciong
        anterior=actual
        actual=next
        if actual==fin:
            visitados.pop(fin)
            visitados[next]=(funciong+grafo[anterior][fin]['peso'])
    
    return visitados



#funcion para mostrar las paradas seleccionadas
def calcular():
    resultado.set("")
    primera.set(entrada1.get())
    fin.set(entrada2.get())
    dicc = astar(primera.get(), fin.get())
    n = 0
    for i in dicc:
        resultado.set(resultado.get() + "->" + i)
        n += 1
        if(n == 3):
            resultado.set(resultado.get() + "\n")
            n = 0
    



root.mainloop()