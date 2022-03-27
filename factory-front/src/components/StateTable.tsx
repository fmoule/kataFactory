import React, {useEffect, useRef, useState} from "react";
import {callGET} from "../service/RobotManagerService";
import {AxiosResponse} from "axios";

export interface ActionState {
    name: string,
    nbRobots: number
}

export function convertIntoActionMap(actions: Array<ActionState>): Map<string, number> {
    let resultMap = new Map<string, number>();
    for (let action of actions) {
        resultMap.set(action.name, action.nbRobots);
    }
    return resultMap;
}

export interface StateTableProps {
    isStarted: boolean,
    endGame: (isGameEnded: boolean) => void
}

export interface RobotManagerState {
    isGameFinished: boolean,
    nbRobots: number,
    amount: number,
    nbFoos: number,
    nbBars: number,
    nbFoobars: number,
    state: Array<ActionState>
}

export const StateTable = (props: StateTableProps) => {
    const [nbrRobots, setNbrRobots] = useState(0);
    const [amount, setAmount] = useState(0);
    const [nbFoobars, setNbFoobars] = useState(0);
    const [nbFoos, setNbFoos] = useState(0);
    const [nbBars, setNbBars] = useState(0);
    const [actionStates, setActionStates] = useState(new Map<string, number>());

    const intervalRef = useRef<NodeJS.Timer>();

    const loadDatas = async () => {
        if (!props.isStarted) {
            return;
        }
        callGET<RobotManagerState>("http://localhost:8080/robotManager/get/state")
            .then((resp: AxiosResponse<RobotManagerState>) => {
                setNbrRobots(resp.data.nbRobots);
                setNbBars(resp.data.nbBars);
                setNbFoos(resp.data.nbFoos);
                setNbFoobars(resp.data.nbFoobars);
                setAmount(resp.data.amount);
                setActionStates(convertIntoActionMap(resp.data.state));
                if (resp.data.isGameFinished) {
                    if (intervalRef.current !== undefined) {
                        clearInterval(intervalRef.current);
                    }
                    props.endGame(true);
                } else {
                    props.endGame(false);
                }
            });
    }

    useEffect(() => {
        let intervalID: NodeJS.Timer = setInterval(() => {
            loadDatas();
        }, 500);
        intervalRef.current = intervalID;
        return () => {clearInterval(intervalID)}
    });

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
                        <td>Mining Bar</td>
                        <td>{(!actionStates.has('miningBar') ? 0 : actionStates.get('miningBar'))}</td>
                    </tr>
                    <tr>
                        <td>Mining Foos</td>
                        <td>{(!actionStates.has('miningFoo') ? 0 : actionStates.get('miningFoo'))}</td>
                    </tr>
                    <tr>
                        <td>Assembly FooBar</td>
                        <td>{(!actionStates.has('assemblyFooBar') ? 0 : actionStates.get('assemblyFooBar'))}</td>
                    </tr>
                    <tr>
                        <td>Sell Foobars</td>
                        <td>{(!actionStates.has('sellingFoobar') ? 0 : actionStates.get('sellingFoobar'))}</td>
                    </tr>
                    <tr>
                        <td>Buy robots</td>
                        <td>{(!actionStates.has('buyingRobot') ? 0 : actionStates.get('buyingRobot'))}</td>
                    </tr>
                    <tr>
                        <td>Check if game ended</td>
                        <td>{(!actionStates.has('endGameAction') ? 0 : actionStates.get('endGameAction'))}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

    </div>);
}