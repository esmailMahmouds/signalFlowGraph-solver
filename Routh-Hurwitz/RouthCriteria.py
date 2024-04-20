import numpy as np
import matplotlib.pyplot as plt

def toDig(string):
    if string[0] == "-":
        if string[1:] == "":
            return -1
        return -1 * float(string[1:])
    else:
        return float(string)

def coefficients(equation):

    #determining the variable
    variable = ""
    for character in equation:
        if character not in "0123456789-+^ ":
            variable = character
            break
    for character in equation:
        if character not in "0123456789-+^ " and character != variable:
            raise ValueError("Invalid equation, multiple variables detected.")
    
    #replacing the variable with "s" and splitting the equation
    equation = equation.replace(variable, "s")
    equation = equation.replace("--", "+")
    equation = equation.replace(" ", "").replace("s^", "s").replace("+", "$").replace("-", "$-")
    equation = equation.split("$")

    maxPower = max([int(term.split("s")[1]) for term in equation if "s" in term and term.split("s")[1] != ""])
    coeffs = [0 for i in range(maxPower + 1)]
    
    for term in equation:
        if term == "":
            continue
        if term[0] == "s" and term.split("s")[1] != "":
            coeffs[maxPower-int(term.split("s")[1])] += 1.0
        elif term[0] == "s": 
            coeffs[maxPower-1] += 1.0
        elif "s" in term and term.split("s")[1] != "":
            coeffs[maxPower-int(term.split("s")[1])] += toDig(term.split("s")[0])
        elif "s" in term:
            coeffs[maxPower-1] += toDig(term.split("s")[0])
        else:
            coeffs[maxPower] += toDig(term)
    return coeffs

def routhConditions(coeffs):
    if all(i>0 for i in coeffs) or all(i<0 for i in coeffs):
        return True
    else:
        return False
    
def table(coeffs):
    routhTable = []
    firstRow = []
    secondRow = []

    for i in range(0, len(coeffs), 2):
        firstRow.append(coeffs[i])
    routhTable.append(firstRow)

    for i in range(1, len(coeffs), 2):
        secondRow.append(coeffs[i])
    if len(coeffs) % 2 != 0:
        secondRow.append(0)
    routhTable.append(secondRow)
    
    for i in range(2, len(coeffs)):
        newRow = []
        for j in range(0, len(firstRow) - i//2):
            newRow.append((routhTable[i-1][0] * routhTable[i-2][j+1] - routhTable[i-2][0] * routhTable[i-1][j+1]) / routhTable[i-1][0])
        for j in range(len(firstRow) - i//2, len(firstRow)):
            newRow.append(0)
        if all(i == 0 for i in newRow) and i < len(coeffs) - 2: # if the row is all zeros and not s1 or s0
            for j in range(0, len(firstRow) - i//2):
                newRow[j] = routhTable[i-1][j] * (len(coeffs)-i-2*j)
        elif newRow[0] == 0:
            newRow[0] = 1e-10
        routhTable.append(newRow)
    
    return routhTable

def stability(routhTable):
    RHSpoles = 0
    for i in range(1, len(routhTable)):
        if routhTable[i][0] * routhTable[i-1][0] < 0:
            RHSpoles += 1
    return RHSpoles

def poles(coeffs):
    poles = np.roots(coeffs)
    RHS_poles = [pole for pole in poles if np.real(pole) > 0]
    return RHS_poles

def main():
    equation = input("Enter the equation: ")
    try:
        coeffs = coefficients(equation)
    except ValueError as e:
        print(e)
        return
    if not routhConditions(coeffs):
        print("System is unstable, Routh table conditions are not met.")
        return
    
    routhTable = table(coeffs)
    for row in routhTable:
        print(row)
    RHS_poles_number = stability(routhTable)
    if(RHS_poles_number == 0):
        print("System is stable")
    else:
        print("System is unstable" + " " + str(RHS_poles_number) + " poles in the right half of the s-plane")
        print("Poles in the right half of the s-plane: " + str(poles(coeffs)))
    
    plot = input("plot the poles? (y/n): ")
    if plot == "y":
        z = np.roots(coeffs)
        plt.scatter(z.real, z.imag)
        plt.xlabel('Re')
        plt.ylabel('I')
        plt.title('System poles in the s-plane')
        plt.axhline(0, color='black',linewidth=0.5)
        plt.axvline(0, color='black',linewidth=0.5)
        plt.grid(color = 'gray', linestyle = '--', linewidth = 0.5)
        plt.show()
    else:
        return

if __name__ == "__main__":
    main()