# -*- coding: utf8 -*-
# A Program for Rubik’s Cube written in Python
# Solution for the Rubik’s Cube by achieving specific 
# sub-goals, specifically using Dedmore’s methods.
# Source:  www.pastebin.com/KwGMujyB

import random
class face(object):
    struc = []
    name = None
    value = None

    def __init__(self, name, value, faces):
        self.struc = []
        self.name = name
        self.value = value
        self.FRONT, self.BACK, self.LEFT, self.RIGHT, self.UP, self.DOWN = faces
        for x in xrange(3):
            self.struc.append([])
            for y in xrange(3):
                self.struc[x].append(value)
        
    def getName(self):
        return self.name

    def num2coord(self, num):
        return (num / 3, num % 3)

    def coord2num(self, coord):
        row,col = coord
        return row*3+col

    def getColumns(self, _2dlist=None):
        if _2dlist == None:
            _2dlist = self.struc
        ret = []
        for i in xrange(len(_2dlist[0])):
            ret.append([])
            for j in xrange(len(_2dlist)):
                ret[i].append(_2dlist[j][i])
        return ret

    def getUpFacePos(self, frontFace, row, col):
        if frontFace == self.FRONT or face == self.UP or frontFace == self.DOWN:
            return (row, col)
        elif frontFace == self.BACK:
            return self.num2coord(8 - self.coord2num((row,col)))
        elif frontFace == self.LEFT:
            cmap = [2,5,8,1,4,7,0,3,6]
            return self.num2coord(cmap[self.coord2num((row,col))])
        elif frontFace == self.RIGHT:
            cmap = [6,3,0,7,4,1,8,5,2]
            return self.num2coord(cmap[self.coord2num((row,col))])
        
    def getDownFacePos(self, frontFace, row, col):
        if frontFace == self.FRONT or frontFace == self.UP or frontFace == self.DOWN:
            return (row, col)
        elif frontFace == self.BACK:
            return self.num2coord(8 - self.coord2num((row,col)))
        elif frontFace == self.LEFT:
            cmap = [6,3,0,7,4,1,8,5,2]
            return self.num2coord(cmap[self.coord2num((row,col))])
        elif frontFace == self.RIGHT:
            cmap = [2,5,8,1,4,7,0,3,6]
            return self.num2coord(cmap[self.coord2num((row,col))])

    def getPosFromUp(self, row, col):
        if self.name == self.FRONT or self.name == self.UP or self.name == self.DOWN:
            return (row, col)
        elif self.name == self.BACK:
            return self.num2coord(8 - self.coord2num((row,col)))
        elif self.name == self.LEFT:
            cmap = [6,3,0,7,4,1,8,5,2]
            return self.num2coord(cmap[self.coord2num((row,col))])
        elif self.name == self.RIGHT:
            cmap = [2,5,8,1,4,7,0,3,6]
            return self.num2coord(cmap[self.coord2num((row,col))])

    def getPosFromDown(self, row, col):
        if self.name == self.FRONT or self.name == self.UP or self.name == self.DOWN:
            return (row, col)
        elif self.name == self.BACK:
            return self.num2coord(8 - self.coord2num((row,col)))
        elif self.name == self.LEFT:
            cmap = [2,5,8,1,4,7,0,3,6]
            return self.num2coord(cmap[self.coord2num((row,col))])
        elif self.name == self.RIGHT:
            cmap = [6,3,0,7,4,1,8,5,2]
            return self.num2coord(cmap[self.coord2num((row,col))])

    def getCoords(self, key):
        x, y = -1, -1
        if len(key) == 2:
            x,y = self.num2coord(key[1])
            key = key[0], x, y
        if key[0] == self.UP:
            x,y = self.getPosFromUp(key[1], key[2])
        elif key[0] == self.DOWN:
            x,y = self.getPosFromDown(key[1], key[2])
        elif self.name == self.UP:
            x,y = self.getUpFacePos(*key)
        elif self.name == self.DOWN:
            x,y = self.getDownFacePos(*key)
        else:
            x,y = key[1], key[2]
        return (x,y)

    def __eq__(self, other):
        if type(other) == str:
            return self.name == other
        else:
            return self.name == other.name

    def __getitem__(self, key):
        x,y=self.getCoords(key)
        return self.struc[x][y]
    
    def __setitem__(self, key, value):
        x,y=self.getCoords(key)
        self.struc[x][y] = value

    def __len__(self):
        return 3

    def __str__(self):
        return str(self.struc)

    def __repr__(self):
        return str(self)

    def isSolved(self):
        c = self.struc[0][0]
        for x in self.struc:
            for y in x:
                if c != y: return False
        return True

    def listRep(self, frontFace):
        r = []
        for i in xrange(9):
            r.append(self[frontFace, i])
        return r

    def reOrder(self, frontFace, newOrder):
        oldDat = self.listRep(frontFace)
        for i in xrange(9):
            self[frontFace, i] = oldDat[newOrder[i]]

    def copy(self):
        r = face(self.name, self.value, (self.FRONT, self.BACK, self.LEFT, self.RIGHT, self.UP, self.DOWN))
        r.struc = []
        for x in xrange(3):
            r.struc.append([])
            for y in xrange(3):
                r.struc[x].append(self.struc[x][y])

        return r
        
class cube(object):
    W, R, B, O, G, Y = 0, 1, 2, 3, 4, 5
    colors = [W, Y, R, O, G, B] # F, B, L, R, U, D
    colLet = {W:"W", R:"R", B:"B", O:"O", G:"G", Y:"Y"}
    FRONT = "F"
    BACK = "B"
    LEFT = "L"
    RIGHT = "R"
    UP = "U"
    DOWN = "D"

    cube = {}
    moveList = []

    lastFace = -1
    recording = False
    
    def __init__(self):
        self.cube = {}
        self.moveList = []
        self.createCube()

    def copy(self):
        r = cube()
        faces = [self.FRONT, self.BACK, self.LEFT, self.RIGHT, self.UP, self.DOWN]
        for i in xrange(len(self.colors)):
            r.cube[faces[i]] = self.cube[faces[i]].copy()
        return r

    def startRecord(self):
        self.moveList = []
        self.recording = True

    def endRecord(self):
        self.recording = False
        
    def createCube(self):
        faces = [self.FRONT, self.BACK, self.LEFT, self.RIGHT, self.UP, self.DOWN]
        for i in xrange(len(self.colors)):
            self.cube[faces[i]] = face(faces[i], self.colors[i], faces)          
                    
    def getColumns(self, frontFace, face):
        ret = []
        for i in xrange(len(face)):
            ret.append([])
            for j in xrange(len(face)):
                ret[i].append(face[(frontFace,j,i)])
        return ret
    
    def getFaces(self, frontFace):
        if frontFace == self.BACK:
            return self.BACK, self.FRONT, self.RIGHT, self.LEFT, self.UP, self.DOWN
        elif frontFace == self.LEFT:
            return self.LEFT, self.RIGHT, self.BACK, self.FRONT, self.UP, self.DOWN
        elif frontFace == self.RIGHT:
            return self.RIGHT, self.LEFT, self.FRONT, self.BACK, self.UP, self.DOWN
        elif frontFace == self.UP:
            return self.UP, self.DOWN, self.LEFT, self.RIGHT, self.BACK, self.FRONT
        elif frontFace == self.DOWN:
            return self.DOWN, self.UP, self.LEFT, self.RIGHT, self.FRONT, self.BACK
        else:
            return self.FRONT, self.BACK, self.LEFT, self.RIGHT, self.UP, self.DOWN

    def inverseMove(self, move):
        return move[0] if len(move)==2 else (move+"'")
        
    def turn(self, face, clockwise=True):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(face)
        if self.recording:
            g = "%s%s" % (face, "" if clockwise else "'")
            self.moveList.append(g)
            f = True
            while f:
                f = False
                if len(self.moveList) > 1:
                    if self.moveList[-1] == self.inverseMove(self.moveList[-2]):
                        self.moveList.pop(); self.moveList.pop()
                        f = True
                if len(self.moveList) > 2:
                    if self.moveList[-1] == self.moveList[-2] == self.moveList[-3]:
                        self.moveList.pop(); self.moveList.pop()
                        self.moveList[-1] = self.inverseMove(self.moveList[-1])
                        f = True
        if clockwise:
            lRep = self.cube[LEFT].listRep(FRONT)
            rRep = self.cube[RIGHT].listRep(FRONT)
            uRep = self.cube[UP].listRep(FRONT)
            dRep = self.cube[DOWN].listRep(FRONT)

            #FRONT
            self.cube[FRONT].reOrder(FRONT, [6,3,0,7,4,1,8,5,2])
            #LEFT
            self.cube[LEFT][FRONT, 2] = dRep[0]
            self.cube[LEFT][FRONT, 5] = dRep[1]
            self.cube[LEFT][FRONT, 8] = dRep[2]
            #DOWN
            self.cube[DOWN][FRONT, 0] = rRep[6]
            self.cube[DOWN][FRONT, 1] = rRep[3]
            self.cube[DOWN][FRONT, 2] = rRep[0]
            #RIGHT
            self.cube[RIGHT][FRONT, 0] = uRep[6]
            self.cube[RIGHT][FRONT, 3] = uRep[7]
            self.cube[RIGHT][FRONT, 6] = uRep[8]
            #UP
            self.cube[UP][FRONT, 6] = lRep[8]
            self.cube[UP][FRONT, 7] = lRep[5]
            self.cube[UP][FRONT, 8] = lRep[2]
        else:
            lRep = self.cube[LEFT].listRep(FRONT)
            rRep = self.cube[RIGHT].listRep(FRONT)
            uRep = self.cube[UP].listRep(FRONT)
            dRep = self.cube[DOWN].listRep(FRONT)

            #FRONT
            self.cube[FRONT].reOrder(FRONT, [2,5,8,1,4,7,0,3,6])
            #LEFT
            self.cube[LEFT][FRONT, 2] = uRep[8]
            self.cube[LEFT][FRONT, 5] = uRep[7]
            self.cube[LEFT][FRONT, 8] = uRep[6]
            #DOWN
            self.cube[DOWN][FRONT, 0] = lRep[2]
            self.cube[DOWN][FRONT, 1] = lRep[5]
            self.cube[DOWN][FRONT, 2] = lRep[8]
            #RIGHT
            self.cube[RIGHT][FRONT, 0] = dRep[2]
            self.cube[RIGHT][FRONT, 3] = dRep[1]
            self.cube[RIGHT][FRONT, 6] = dRep[0]
            #UP
            self.cube[UP][FRONT, 6] = rRep[0]
            self.cube[UP][FRONT, 7] = rRep[3]
            self.cube[UP][FRONT, 8] = rRep[6]
            

    def turnMiddle(self, face):
        if self.recording:
            clockwise = face in (self.RIGHT, self.UP, self.BACK)
            dic = {self.LEFT:"M",self.RIGHT:"M",
                   self.UP:"E",self.DOWN:"E",
                   self.FRONT:"S",self.BACK:"S"}
            g = "%s%s" % (dic[face], "" if clockwise else "'")
            self.moveList.append(g)
            f = True
            while f:
                f = False
                if len(self.moveList) > 1:
                    if self.moveList[-1] == self.inverseMove(self.moveList[-2]):
                        self.moveList.pop(); self.moveList.pop()
                        f = True
                if len(self.moveList) > 2:
                    if self.moveList[-1] == self.moveList[-2] == self.moveList[-3]:
                        self.moveList.pop(); self.moveList.pop()
                        self.moveList[-1] = self.inverseMove(self.moveList[-1])
                        f = True
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(face)
        up = [self.cube[UP][(self.cube[FRONT], 3)], self.cube[UP][(self.cube[FRONT], 4)],
              self.cube[UP][(self.cube[FRONT], 5)]]
        left = [self.cube[LEFT][(self.cube[FRONT], 1)], self.cube[LEFT][(self.cube[FRONT], 4)],
              self.cube[LEFT][(self.cube[FRONT], 7)]]
        down = [self.cube[DOWN][(self.cube[FRONT], 3)], self.cube[DOWN][(self.cube[FRONT], 4)],
              self.cube[DOWN][(self.cube[FRONT], 5)]]
        right = [self.cube[RIGHT][(self.cube[FRONT], 1)], self.cube[RIGHT][(self.cube[FRONT], 4)],
              self.cube[RIGHT][(self.cube[FRONT], 7)]]

        self.cube[UP][(self.cube[FRONT], 3)] = left[2]
        self.cube[UP][(self.cube[FRONT], 4)] = left[1]
        self.cube[UP][(self.cube[FRONT], 5)] = left[0]

        self.cube[LEFT][(self.cube[FRONT], 1)] = down[0]
        self.cube[LEFT][(self.cube[FRONT], 4)] = down[1]
        self.cube[LEFT][(self.cube[FRONT], 7)] = down[2]

        self.cube[DOWN][(self.cube[FRONT], 3)] = right[2]
        self.cube[DOWN][(self.cube[FRONT], 4)] = right[1]
        self.cube[DOWN][(self.cube[FRONT], 5)] = right[0]

        self.cube[RIGHT][(self.cube[FRONT], 1)] = up[0]
        self.cube[RIGHT][(self.cube[FRONT], 4)] = up[1]
        self.cube[RIGHT][(self.cube[FRONT], 7)] = up[2]
        
        
        

    def randomize(self, turns = 1000):
        sides = [self.FRONT, self.BACK, self.LEFT, self.RIGHT, self.UP, self.DOWN]
        for i in xrange(turns):
            if random.random() > .5:
                self.turn(random.choice(sides))
            else:
                self.turnMiddle(random.choice(sides))
                
    def isSolved(self):
        for s in self.cube:
            if not self.cube[s].isSolved(): return False
        return True

    def __str__(self):
        x = ""
        for l in self.cube:
            x += l+" : "+str(self.cube[l])+"\n"
        return x[:-1]
        #return self.formatStr()

    def strSide(self, face, replCol=True):
        x = """%i %i %i
%i %i %i
%i %i %i""" % (self.cube[face][0][0],self.cube[face][0][1],self.cube[face][0][2],
               self.cube[face][1][0],self.cube[face][1][1],self.cube[face][1][2],
               self.cube[face][2][0],self.cube[face][2][1],self.cube[face][2][2])
        if replCol:
            for col in self.colLet: x = x.replace(str(col), self.colLet[col])
        return x
        
    def strCube(self,face=-1,replCol=True):
        faces = self.getFaces(face)
        o = "FBLRUD"
        
        x = """
        U0 U1 U2
        U3 U4 U5   
        U6 U7 U8    
 L0 L1 L2  F0 F1 F2  R0 R1 R2  B0 B1 B2
 L3 L4 L5  F3 F4 F5  R3 R4 R5  B3 B4 B5
 L6 L7 L8  F6 F7 F8  R6 R7 R8  B6 B7 B8
        D0 D1 D2
        D3 D4 D5
        D6 D7 D8
"""
        j = 0
        for face in faces:
            for i in xrange(9):
                s = "%s%d" % (o[j], i)
                x = x.replace(s, str(self.cube[face][faces[0], i]))
            j+=1

        if replCol:
            for col in self.colLet: x = x.replace(str(col), self.colLet[col])

        return x

    def wrongWay(self,face=-1):
        code = {"W":1,"O":2,"B":3,"R":4,"G":5,"Y":6}
        o = "UFLBRD"
        r = ""
        for face in o:
            for i in xrange(9):
                r += str(code[self.colLet[self.cube[face][self.FRONT, i]]])
        return r
        
    def getColName(self, num):
        return self.colLet[num]
    
    def __repr__(self):
        return self.strCube("U")#self.lastFace)

    def primeCube(self, frontFace = -1):
        if frontFace == -1: frontFace = self.FRONT
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        primeCol = self.cube[UP][(self.cube[FRONT], 8)]
        isPrime = False
        stFace = FRONT
        i = 0
        while self.cube[UP][(self.cube[FRONT], 8)] != self.cube[UP][(self.cube[FRONT], 4)] and i < 4:
            self.turnMiddle(FRONT)
            i+=1
        if self.cube[UP][(self.cube[FRONT], 8)] != self.cube[UP][(self.cube[FRONT], 4)]:
            i = 0
            while self.cube[UP][(self.cube[FRONT], 8)] != self.cube[UP][(self.cube[FRONT], 4)] and i < 4:
                self.turnMiddle(LEFT)
                i += 1
        if self.cube[UP][(self.cube[FRONT], 8)] == self.cube[UP][(self.cube[FRONT], 4)]:
            return FRONT
        else:
            return False

    def cornerHasColsBR(self, frontFace, needColors):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        colors = (self.cube[FRONT][(self.cube[FRONT], 8)],
                self.cube[RIGHT][(self.cube[FRONT], 6)],
                self.cube[DOWN][(self.cube[FRONT], 2)])
        cnt = 0
        for color in needColors:
            if color in colors:
                cnt += 1
        return cnt >= 2

    def cornerHasColsTR(self, frontFace, needColors):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        colors = (self.cube[FRONT][(self.cube[FRONT], 2)],
                self.cube[RIGHT][(self.cube[FRONT], 0)],
                self.cube[UP][(self.cube[FRONT], 8)])
        cnt = 0
        for color in needColors:
            if color in colors:
                cnt += 1
        return cnt >= 2

    def cornerHasColsTL(self, frontFace, needColors):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        colors = (self.cube[FRONT][(self.cube[FRONT], 0)],
                self.cube[LEFT][(self.cube[FRONT], 2)],
                self.cube[UP][(self.cube[FRONT], 6)])
        cnt = 0
        for color in needColors:
            if color in colors:
                cnt += 1
        return cnt >= 2

    def stepOneCompleteFace(self, frontFace, upCol):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        f0 = self.cube[FRONT][(self.cube[FRONT], 0)]
        f1 = self.cube[FRONT][(self.cube[FRONT], 2)]
        u0 = self.cube[UP][(self.cube[FRONT], 6)]
        u1 = self.cube[UP][(self.cube[FRONT], 8)]
        return (u0 == upCol) and (u0 == u1) and (f0 == f1)
    
    def stepOne(self, frontFace=-1, check=True):
        counter = 0
        skip = []
        completedFaces = []
        if frontFace == -1: frontFace = self.FRONT
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        # Assume we just primed
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
        upCol = self.cube[UP][(self.cube[FRONT], 4)]
        skipCount = 0
        while counter < 20 and len(completedFaces) < 4:

            if self.stepOneCompleteFace(FRONT, upCol):
                if FRONT not in completedFaces: completedFaces.append(FRONT)
                #print "%s is completed" % FRONT
                FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
                continue
            needCols = (upCol, self.cube[FRONT][self.cube[FRONT], 0])
            if self.cube[FRONT][(self.cube[FRONT], 2)] == upCol and self.cornerHasColsTR(FRONT, needCols): # Algo 4
                #print "Using Algo 4"
                self.turn(FRONT)
                self.turn(DOWN)
                self.turn(FRONT, False)
                self.turn(DOWN)
                self.turn(DOWN)
                self.turn(RIGHT, False)
                self.turn(DOWN)
                self.turn(RIGHT)
                if self.stepOneCompleteFace(FRONT, upCol):
                    completedFaces.append(FRONT)
                else:
                    #print "Algo failed."
                    FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            elif self.cube[RIGHT][(self.cube[FRONT], 0)] == upCol and self.cornerHasColsTR(FRONT, needCols): # Algo 5
                #print "Using Algo 5"
                self.turn(RIGHT, False)
                self.turn(DOWN, False)
                self.turn(RIGHT)
                self.turn(DOWN)
                self.turn(RIGHT, False)
                self.turn(DOWN, False)
                self.turn(RIGHT)
                if self.stepOneCompleteFace(FRONT, upCol):
                    completedFaces.append(FRONT)
                else:
                    #print "Algo failed."
                    FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            else:
                foundCorner = True
                i = 0
                while not self.cornerHasColsBR(FRONT, needCols) and i < 4:
                    self.turn(DOWN)
                    i += 1
                if i == 4 and not self.cornerHasColsBR(FRONT, needCols):
                    foundCorner = False
                if foundCorner and self.cube[RIGHT][(self.cube[FRONT], 6)] == upCol: # Algo 1
                    #print "Using Algo 1"
                    self.turn(RIGHT, False)
                    self.turn(DOWN, False)
                    self.turn(RIGHT)
                    
                    if self.stepOneCompleteFace(FRONT, upCol):
                        completedFaces.append(FRONT)
                    else:
                        #print "Algo failed."
                        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
                elif foundCorner and self.cube[FRONT][(self.cube[FRONT], 8)] == upCol: # Algo 2
                    #print "Using Algo 2"
                    self.turn(DOWN, False)
                    self.turn(RIGHT, False)
                    self.turn(DOWN)
                    self.turn(RIGHT)
                    if self.stepOneCompleteFace(FRONT, upCol):
                        completedFaces.append(FRONT)
                    else:
                        #print "Algo failed."
                        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
                elif foundCorner and self.cube[DOWN][(self.cube[FRONT], 2)] == upCol: # Algo 3
                    #print "Using Algo 3"
                    self.turn(RIGHT, False)
                    self.turn(DOWN)
                    self.turn(RIGHT)
                    self.turn(DOWN)
                    self.turn(DOWN)
                    self.turn(RIGHT, False)
                    self.turn(DOWN, False)
                    self.turn(RIGHT)
                    if self.stepOneCompleteFace(FRONT, upCol):
                        completedFaces.append(FRONT)
                    else:
                        #print "Algo failed."
                        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
                else:
                    #print "Skipping Face", FRONT
                    skipCount += 1
                    if skipCount == 3:
                        self.turn(RIGHT)
                        self.turn(DOWN)
                        skipCount = 0
                    #return False
                    FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            counter += 1
        if self.stepOneComplete(FRONT):
            self.lastFace = FRONT
            return FRONT
        elif check:
            return self.stepOne(frontFace, False)
        else:
            return False


    def stepOneComplete(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        midCol = self.cube[UP][(self.cube[UP], 4)]
        if self.cube[UP][(self.cube[UP], 0)] != midCol: return False
        if self.cube[UP][(self.cube[UP], 2)] != midCol: return False
        if self.cube[UP][(self.cube[UP], 6)] != midCol: return False
        if self.cube[UP][(self.cube[UP], 8)] != midCol: return False
        for i in xrange(4):
            if self.cube[FRONT][(self.cube[FRONT], 0)] != self.cube[FRONT][(self.cube[FRONT], 2)]: return False
            FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
        return True

    def stepTwo(self, frontFace, check=True):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        if not self.stepOneComplete(FRONT):
            raise RuntimeError("Step 2 conditions not met.")
        upCol = self.cube[UP][(self.cube[FRONT], 4)]

        i = 0
        while not self.allFirstLayer(FRONT) and i < 128:
            faceCol = self.cube[FRONT][(self.cube[FRONT], 0)]
            if self.cube[DOWN][self.cube[FRONT], 1] == upCol and self.cube[FRONT][self.cube[FRONT], 7] == faceCol:
                self.turnMiddle(LEFT)
                self.turn(DOWN, False)
                self.turn(DOWN, False)
                self.turnMiddle(RIGHT)
            elif self.cube[FRONT][self.cube[FRONT], 7] == upCol and self.cube[DOWN][self.cube[FRONT], 1] == faceCol:
                self.turn(DOWN, False)
                self.turnMiddle(LEFT)
                self.turn(DOWN)
                self.turnMiddle(RIGHT)
            elif self.cube[RIGHT][self.cube[FRONT], 3] == upCol and self.cube[FRONT][self.cube[FRONT], 5] == faceCol:
                self.turnMiddle(DOWN)
                self.turn(FRONT)
                self.turnMiddle(UP)
                self.turn(FRONT, False)
            elif self.cube[FRONT][self.cube[FRONT], 5] == upCol and self.cube[RIGHT][self.cube[FRONT], 3] == faceCol:
                self.turnMiddle(DOWN)
                self.turn(FRONT, False)
                self.turnMiddle(UP)
                self.turnMiddle(UP)
                self.turn(FRONT)
            elif self.cube[FRONT][self.cube[FRONT], 1] == upCol and self.cube[UP][self.cube[FRONT], 7] == faceCol:
                self.turnMiddle(LEFT)
                self.turn(DOWN, False)
                self.turn(DOWN, False)
                self.turnMiddle(RIGHT)
                self.turn(DOWN, False)
                self.turnMiddle(LEFT)
                self.turn(DOWN)
                self.turnMiddle(RIGHT)
            FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            i += 1
            if i % 4 == 0:
                self.turn(UP)

        if self.allFirstLayer(FRONT):
            return FRONT
        else:
            return False

    def firstLayer(self, face):
        r = True
        lr = self.cube[face].listRep(face)[:3]
        fc = lr[0]
        for facelet in lr:
            if facelet != fc: r = False; break
        return r

    def allFirstLayer(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        for cFace in [FRONT, RIGHT, BACK, LEFT]:
            if not self.firstLayer(cFace):
                return False
        return True
    
    def firstTwoLayers(self, face):
        r = True
        lr = self.cube[face].listRep(face)[:6]
        fc = lr[0]
        for facelet in lr:
            if facelet != fc: r = False; break
        return r

    def allFirstTwoLayers(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        for cFace in [FRONT, RIGHT, BACK, LEFT]:
            if not self.firstTwoLayers(cFace):
                return False
        return True
                
    def stepThree(self, frontFace, check=True):
        if not self.allFirstLayer(frontFace):
            raise RuntimeError("Step 3 conditions not met.")
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        while self.cube[FRONT][self.cube[FRONT], 1] != self.cube[FRONT][self.cube[FRONT], 4]:
            self.turnMiddle(UP)

        i = 0
        j = 0
        perf = False
        while not self.allFirstTwoLayers(frontFace) and j < 64:
            faceCol = self.cube[FRONT][(self.cube[FRONT], 4)]
            leftCol = self.cube[LEFT][(self.cube[FRONT], 4)]
            rightCol = self.cube[RIGHT][(self.cube[FRONT], 4)]
            tCol = self.cube[FRONT][(self.cube[FRONT], 7)]
            dCol = self.cube[DOWN][(self.cube[FRONT], 1)]
            if tCol == faceCol:
                if dCol == leftCol:
                    self.turn(DOWN)
                    self.turn(LEFT)
                    self.turn(DOWN, False)
                    self.turn(LEFT, False)
                    self.turn(DOWN, False)
                    self.turn(FRONT, False)
                    self.turn(DOWN)
                    self.turn(FRONT)
                    perf = True
                elif dCol == rightCol:
                    self.turn(DOWN, False)
                    self.turn(RIGHT, False)
                    self.turn(DOWN)
                    self.turn(RIGHT)
                    self.turn(DOWN)
                    self.turn(FRONT)
                    self.turn(DOWN, False)
                    self.turn(FRONT, False)
                    perf = True
            FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            i += 1
            if i % 4 == 0:
                j += 1
                if j % 4 == 0:
                    F, B, L, R, U, D = self.getFaces(FRONT)
                    for k in xrange(4):
                        if self.cube[F][(self.cube[F], 3)] != self.cube[F][(self.cube[F], 4)] or \
                           self.cube[L][(self.cube[F], 5)] != self.cube[L][(self.cube[F], 4)]:
                            self.turn(D)
                            self.turn(L)
                            self.turn(D, False)
                            self.turn(L, False)
                            self.turn(D, False)
                            self.turn(F, False)
                            self.turn(D)
                            self.turn(F)
                            break
                        elif self.cube[F][(self.cube[F], 5)] != self.cube[F][(self.cube[F], 4)] or \
                             self.cube[R][(self.cube[F], 3)] != self.cube[R][(self.cube[F], 4)]:   
                            self.turn(D, False)
                            self.turn(R, False)
                            self.turn(D)
                            self.turn(R)
                            self.turn(D)
                            self.turn(F)
                            self.turn(D, False)
                            self.turn(F, False)
                            break
                        F, B, L, R, U, D = self.getFaces(R)
                else:
                    self.turn(DOWN)
                

        return FRONT if self.allFirstTwoLayers(frontFace) else False
        faces = [FRONT, RIGHT, BACK, LEFT]
        i = 0
        tPossible = 0
        while not self.allFirstTwoLayers(FRONT) and i < 40:
            face = faces[i % 4]
            F, B, L, R, U, D = self.getFaces(face)
            #if tPossible == 0:
            #    print face
            tPossible += 1
            j = 0
            while j < 4:
                midCol = self.cube[F][self.cube[F], 4]
                if self.cube[F][self.cube[F], 7] == midCol and (self.cube[D][self.cube[F], 1] in (self.cube[L][self.cube[F], 4], self.cube[R][self.cube[F], 4])):
                    tPossible = 0
                    if self.cube[D][self.cube[F], 1] == self.cube[L][self.cube[F], 4]:
                        self.turn(D)
                        self.turn(L)
                        self.turn(D, False)
                        self.turn(L, False)
                        self.turn(D, False)
                        self.turn(F, False)
                        self.turn(D)
                        self.turn(F)
                    elif self.cube[D][self.cube[F], 1] == self.cube[R][self.cube[F], 4]:
                        self.turn(D, False)
                        self.turn(R, False)
                        self.turn(D)
                        self.turn(R)
                        self.turn(D)
                        self.turn(F)
                        self.turn(D, False)
                        self.turn(F, False)
                else:
                    self.turn(D)
                    j += 1
            if tPossible == 5:
                for face in faces:
                    F, B, L, R, U, D = self.getFaces(face)
                    midCol = self.cube[F][self.cube[F], 4]
                    if self.cube[F][self.cube[F], 3] != midCol:
                        self.turn(D)
                        self.turn(L)
                        self.turn(D, False)
                        self.turn(L, False)
                        self.turn(D, False)
                        self.turn(F, False)
                        self.turn(D)
                        self.turn(F)
                    elif self.cube[F][self.cube[F], 5] != midCol:
                        self.turn(D, False)
                        self.turn(R, False)
                        self.turn(D)
                        self.turn(R)
                        self.turn(D)
                        self.turn(F)
                        self.turn(D, False)
                        self.turn(F, False)
                tPossible = 0
            i += 1
        if self.allFirstTwoLayers(FRONT):
            return FRONT
        else:
            if check:
                return self.stepThree(FRONT, False)
            else:
                return False

    def switch1and2(self, frontFace):
        F, B, L, R, U, D = self.getFaces(frontFace)
        self.turn(L, False)
        self.turn(U, False)
        self.turn(L)
        self.turn(F)
        self.turn(U)
        self.turn(F, False)
        self.turn(L, False)
        self.turn(U)
        self.turn(L)
        self.turn(U)
        self.turn(U)

    def switch1and3(self, frontFace):
        F, B, L, R, U, D = self.getFaces(frontFace)
        self.turn(U)
        self.turn(L, False)
        self.turn(U, False)
        self.turn(L)
        self.turn(F)
        self.turn(U)
        self.turn(F, False)
        self.turn(L, False)
        self.turn(U)
        self.turn(L)
        self.turn(U)

    def colorPos1(self, frontFace):
        F, B, L, R, U, D = self.getFaces(frontFace)
        return (self.cube[F][(self.cube[F], 2)],
                self.cube[U][(self.cube[F], 8)],
                self.cube[R][(self.cube[F], 0)])

    def colorPos2(self, frontFace):
        F, B, L, R, U, D = self.getFaces(frontFace)
        return (self.cube[F][(self.cube[F], 0)],
                self.cube[U][(self.cube[F], 6)],
                self.cube[L][(self.cube[F], 2)])

    def colorPos3(self, frontFace):
        F, B, L, R, U, D = self.getFaces(frontFace)
        return (self.cube[R][(self.cube[F], 2)],
                self.cube[U][(self.cube[F], 2)],
                self.cube[B][(self.cube[F], 0)])

    def colorPos4(self, frontFace):
        F, B, L, R, U, D = self.getFaces(frontFace)
        return (self.cube[B][(self.cube[F], 2)],
                self.cube[U][(self.cube[F], 0)],
                self.cube[L][(self.cube[F], 0)])

    def sameColors(self, one, two):
        for color in one:
            if color not in two: return False
        return True

    def stepFour(self, frontFace):
        if not self.allFirstTwoLayers(frontFace):
            raise RuntimeError("Step 4 conditions not met.")
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        self.turn(FRONT); self.turn(FRONT)
        self.turnMiddle(FRONT); self.turnMiddle(FRONT)
        self.turn(BACK, False); self.turn(BACK, False)

        upCol = self.cube[UP][(self.cube[FRONT], 4)]
        needColors = (upCol, self.cube[FRONT][(self.cube[FRONT], 4)])

        order = [
            (self.cube[FRONT][(self.cube[FRONT], 4)], self.cube[RIGHT][(self.cube[FRONT], 4)], self.cube[UP][(self.cube[FRONT], 4)]),
            (self.cube[FRONT][(self.cube[FRONT], 4)], self.cube[LEFT][(self.cube[FRONT], 4)], self.cube[UP][(self.cube[FRONT], 4)]),
            (self.cube[BACK][(self.cube[FRONT], 4)], self.cube[RIGHT][(self.cube[FRONT], 4)], self.cube[UP][(self.cube[FRONT], 4)]),
            (self.cube[BACK][(self.cube[FRONT], 4)], self.cube[LEFT][(self.cube[FRONT], 4)], self.cube[UP][(self.cube[FRONT], 4)])
        ]

        while not self.sameColors(self.colorPos4(FRONT), order[3]):
            self.turn(UP)

        cube1 = self.colorPos1(FRONT)
        cube4 = self.colorPos4(FRONT)
        pos = 3
        #for i in xrange(len(order)):
        #    if self.sameColors(order[i], cube4):
        #        pos = i
        #        break
        #print pos
        if not self.sameColors(order[0], cube1):
            if self.sameColors(self.colorPos2(FRONT), order[0]):
                self.switch1and2(FRONT)
            elif self.sameColors(self.colorPos3(FRONT), order[0]):
                self.switch1and3(FRONT)
            else:
                raise RuntimeError("Unexpected order?")

        #while not self.sameColors(self.colorPos1(FRONT), order[0]):
        #    self.turn(UP)

        if not self.sameColors(order[1], self.colorPos2(FRONT)): # 2 and 3 are swapped
            self.switch1and3(FRONT)
            self.switch1and2(FRONT)
            self.switch1and3(FRONT)
            
        
        c = [self.colorPos1(FRONT),self.colorPos2(FRONT),self.colorPos3(FRONT),self.colorPos4(FRONT)]

        for o in xrange(len(order)):
            if not self.sameColors(order[o], c[o]): print "FAILURE %d" % (o+1)

        while not self.sameColors(self.colorPos1(FRONT), order[0]):
            self.turn(UP)

        return FRONT

    def modelOne(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        return self.cube[UP][(self.cube[FRONT], 4)] == self.cube[UP][(self.cube[FRONT], 8)] == \
               self.cube[FRONT][(self.cube[FRONT], 0)]

    def modelTwo(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        return self.cube[UP][(self.cube[FRONT], 4)] == self.cube[RIGHT][(self.cube[FRONT], 0)] == \
               self.cube[RIGHT][(self.cube[FRONT], 2)]

    def modelThree(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        return self.cube[UP][(self.cube[FRONT], 4)] == self.cube[UP][(self.cube[FRONT], 8)] == \
               self.cube[RIGHT][(self.cube[FRONT], 2)];

    def stepFiveAlgo(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        self.turn(LEFT, False)
        self.turn(UP, False)
        self.turn(LEFT)
        self.turn(UP, False)
        self.turn(LEFT, False)
        self.turn(UP, False)
        self.turn(UP, False)
        self.turn(LEFT)
        self.turn(UP, False)
        self.turn(UP, False)

    def upCornersDone(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        return self.cube[UP][(self.cube[FRONT], 0)] == self.cube[UP][(self.cube[FRONT], 2)] == \
               self.cube[UP][(self.cube[FRONT], 4)] ==  self.cube[UP][(self.cube[FRONT], 6)] == \
                self.cube[UP][(self.cube[FRONT], 8)]
               
        
    def stepFive(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        f = True
        m1 = None
        while True:
            if self.modelOne(FRONT):
                m1 = 10
                break
            elif self.modelTwo(FRONT):
                m1 = 20
                break
            elif self.modelThree(FRONT):
                m1 = 30
                break
            else:
                if f:
                    self.stepFiveAlgo(FRONT)
                    f = False
                FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
        self.stepFiveAlgo(FRONT)
        while not self.upCornersDone(FRONT):
            if self.modelOne(FRONT) and m1 != 1:
                self.stepFiveAlgo(FRONT)
            elif self.modelTwo(FRONT) and m1 != 2:
                self.stepFiveAlgo(FRONT)
            elif self.modelThree(FRONT) and m1 != 3:
                self.stepFiveAlgo(FRONT)
            else:
                FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
        return FRONT

    def sixAlgo(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        self.turnMiddle(RIGHT)
        self.turn(UP, False)
        self.turnMiddle(LEFT)
        self.turn(UP, False)
        self.turn(UP, False)
        self.turnMiddle(RIGHT)
        self.turn(UP, False)
        self.turnMiddle(LEFT)

    def stepSix(self, frontFace):
        DEDH = 0
        FISH = 1
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        j = 0
        while not self.isSolved() and j < 15:
            pairs = []
            for i in xrange(4):
                if self.cube[FRONT][(self.cube[FRONT], 1)] == self.cube[UP][(self.cube[FRONT], 4)] and \
                   self.cube[FRONT][(self.cube[FRONT], 4)] == self.cube[UP][(self.cube[FRONT], 7)] and \
                   (self.cube[UP][(self.cube[FRONT], 4)], self.cube[FRONT][(self.cube[FRONT], 4)]) not in pairs:
                    self.sixAlgo(FRONT)
                    pairs.append(self.cube[FRONT][(self.cube[FRONT], 4)])
                FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            if len(pairs) == 0: self.sixAlgo(FRONT)
            pattern = None
            for i in xrange(4):
                if self.cube[UP][(self.cube[FRONT], 7)] != self.cube[UP][(self.cube[FRONT], 4)] and \
                   self.cube[UP][(self.cube[FRONT], 5)] != self.cube[UP][(self.cube[FRONT], 4)]:
                    pattern = FISH
                    break
                elif self.cube[UP][(self.cube[FRONT], 3)] != self.cube[UP][(self.cube[FRONT], 4)] and \
                     self.cube[UP][(self.cube[FRONT], 5)] != self.cube[UP][(self.cube[FRONT], 4)]:
                    pattern = DEDH
                    break
                FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            if pattern == FISH:
                self.dedmoreFish(FRONT)
            else:
                self.dedmoreH(FRONT)
            if not self.isSolved():
                self.dedmoreH(FRONT)
            j += 1

        return self.isSolved()

    def solveCube(self):
        self.startRecord()
        faces = [self.FRONT, self.DOWN, self.BACK, self.UP, self.LEFT, self.RIGHT]
        for i in xrange(50):
            if (i+1) % 3 == 0: self.randomize(5)
            if self.isSolved(): break
            s = self.primeCube(faces[i % len(faces)])
            if not s:
                continue
            if self.isSolved(): break
            s = self.stepOne(s)
            if not s:
                continue
            if self.isSolved(): break
            s = self.stepTwo(s)
            if not s:
                continue
            if self.isSolved(): break
            s = self.stepThree(s)
            if not s:
                continue
            if self.isSolved(): break
            s = self.stepFour(s)
            if not s:
                continue
            if self.isSolved(): break
            s = self.stepFive(s)
            if not s:
                continue
            if self.isSolved(): break
            s = self.stepSix(s)
            if not s:
                continue
            if self.isSolved(): break
        self.endRecord()
        if self.isSolved():
            print "Solved in %d moves." % len(self.moveList)
            print "".join(self.moveList)
        return self.isSolved()

    def dedmoreH(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        self.turn(RIGHT, False)
        self.turnMiddle(UP)
        self.turn(RIGHT, False)
        self.turn(RIGHT, False)
        self.turnMiddle(UP)
        self.turnMiddle(UP)
        self.turn(RIGHT, False)
        self.turn(UP, False)
        self.turn(UP, False)
        self.turn(RIGHT)
        self.turnMiddle(DOWN)
        self.turnMiddle(DOWN)
        self.turn(RIGHT, False)
        self.turn(RIGHT, False)
        self.turnMiddle(DOWN)
        self.turn(RIGHT)
        self.turn(UP, False)
        self.turn(UP, False)

    def dedmoreFish(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        self.turn(FRONT, False)
        self.turn(LEFT, False)
        self.turn(RIGHT, False)
        self.turnMiddle(UP)
        self.turn(RIGHT, False)
        self.turn(RIGHT, False)
        self.turnMiddle(UP)
        self.turnMiddle(UP)
        self.turn(RIGHT, False)
        self.turn(UP, False) 
        self.turn(UP, False)
        self.turn(RIGHT)
        self.turnMiddle(DOWN)
        self.turnMiddle(DOWN)
        self.turn(RIGHT, False)
        self.turn(RIGHT, False)
        self.turnMiddle(DOWN)
        self.turn(RIGHT)
        self.turn(UP, False)
        self.turn(UP, False)
        self.turn(LEFT)
        self.turn(FRONT)

    def stepSeven(self, frontFace):
        FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(frontFace)
        FISH = 0
        DEDH = 1
        pattern = None

        numFlipped = 0
        for i in xrange(4):
            if self.cube[UP][(self.cube[FRONT], 7)] != self.cube[UP][(self.cube[FRONT], 4)] or \
               self.cube[FRONT][(self.cube[FRONT], 1)] != self.cube[FRONT][(self.cube[FRONT], 0)]:
                numFlipped += 1
        print numFlipped
        if numFlipped > 2:
            self.dedmoreH(FRONT)

            FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)

        while pattern == None:
            for i in xrange(4):
                if self.cube[UP][(self.cube[FRONT], 7)] != self.cube[UP][(self.cube[FRONT], 4)] and \
                   self.cube[UP][(self.cube[FRONT], 5)] != self.cube[UP][(self.cube[FRONT], 4)]:
                    pattern = FISH
                    break
                elif self.cube[UP][(self.cube[FRONT], 3)] != self.cube[UP][(self.cube[FRONT], 4)] and \
                     self.cube[UP][(self.cube[FRONT], 5)] != self.cube[UP][(self.cube[FRONT], 4)]:
                    pattern = DEDH
                    break
                FRONT, BACK, LEFT, RIGHT, UP, DOWN = self.getFaces(RIGHT)
            if pattern == None:
                self.dedmoreH(FRONT)
        if pattern == FISH:
            self.dedmoreFish(FRONT)
        elif pattern == DEDH:
            self.dedmoreH(FRONT)
            
if __name__ == "__main__":
    print "Rubik's Cube Solver"
    print "Type v to view the cube"
    print "Type r to randomize the cube"
    print "Type s to solve the cube"
    print "Type q to quit"
    c = cube()
    command = ""
    while command != "q":
        command = raw_input(">>> ")[0].lower()
        if command == "v":
            print c.strCube()
        elif command == "r":
            c.randomize()
            print "Cube randomized"
        elif command == "s":
            c.solveCube()

