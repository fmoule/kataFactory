import React, {useEffect, useState} from "react";
import {callPUT} from "../service/RobotManagerService";

export interface HeaderProps {
    setStarted: (val: boolean) => void,
    isGameEnded: boolean
}

export const Header = (props: HeaderProps) => {
    const [message, setMessage] = useState("");

    useEffect(() => {
        if (props.isGameEnded) {
            showMessage("Jeu fini !!");
        }
    });

    //// Méthodes :

    const showMessage = (msg: string) => {
        setMessage(msg);
        setTimeout(() => {
            setMessage("")
        }, 1500);
    };

    const startBack = () => {
        callPUT<string>("http://localhost:8080/robotManager/start")
            .then((axioResp) => {
                props.setStarted(true);
                showMessage("RobotManager started !!")
            })
            .catch((reason) => {
                showMessage("ERROR :" + reason);
                props.setStarted(false);
            });
    };

    const stopBack = () => {
        callPUT<string>("http://localhost:8080/robotManager/stop")
            .then((axioResp) => {
                props.setStarted(false);
                showMessage("RobotManager stopped !!")
            })
            .catch((reason) => {
                showMessage("ERROR :" + reason);
                props.setStarted(false);
            });
    };

    return (<div className={"row"}>
        <div className={"col-2"}>
            <button type="button" className="btn btn-primary" onClick={(event) => {startBack();}}>Start</button>
        </div>
        <div className={"col-2"}>
            <button type="button" className="btn btn-danger" onClick={(event) => {stopBack();}}>Stop</button>
        </div>
        <div className={"col-8"}>{message}</div>
    </div>);
}