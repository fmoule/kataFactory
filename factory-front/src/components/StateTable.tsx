import React, {useState} from "react";
//  {"nbFoobars":2,"amount":0,"nbRobots":2,"nbFoos":5,"state":[{"nbRobots":2,"name":"sellingFoobar"}],"nbBars":5}

export interface ActionState {
    name: string,
    nbRobots: number
}

export function convertIntoActionMap(actions: Array<ActionState>) : Map<string, number> {
    let resultMap = new Map<string, number>();
    for (let action of actions) {
        resultMap.set(action.name, action.nbRobots);
    }
    return resultMap;
}

export interface StateTableProps {
    isStarted: boolean
}

export const StateTable = (props: StateTableProps) => {
    const [nbrRobots, setNbrRobots] = useState(0);
    const [amount, setAmount] = useState(0);
    const [nbFoobars, setNbFoobars] = useState(0);
    const [nbFoos, setNbFoos] = useState(0);
    const [nbBars, setNbBars] = useState(0);

    return (<div className={"row"}>
        <div className={"row"}>
            <div className={"col"}>
                Nbre robots: {nbrRobots}
            </div>
            <div className={"col"}>
                Amount: {amount}
            </div>
            <div className={"col"}>
                Nbr Foobars: {nbFoobars}
            </div>
            <div className={"col"}>
                Nbr Foos: {nbFoos}
            </div>
            <div className={"col"}>
                Nbr Bars: {nbBars}
            </div>
        </div>
        <div className={"row"}>
            <div className={"col-8"}>
                <table className={"table"}>
                    <thead>
                    <tr>
                        <th scope="col">Etat</th>
                        <th scope="col">Nombre de robot</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>miningBar</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>miningFoos</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>assemblyFooBar</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>sellingFoobar</td>
                        <td>0</td>
                    </tr>
                    <tr>
                        <td>buyingRobot</td>
                        <td>0</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>);
}