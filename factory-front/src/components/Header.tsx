import React, {useEffect, useState} from "react";
import {callGET, callPUT} from "../service/RobotManagerService";
import {AxiosResponse} from "axios";

export interface HeaderProps {
    setStarted: (val: boolean) => void;

}

export const Header = (props: HeaderProps) => {
    const [message, setMessage] = useState("");

    const showMessage = (msg: string) => {
        setMessage(msg);
        setTimeout(() => {
            setMessage("")
        }, 1500);
    };

    const onClickStartButton = (event: React.MouseEvent<HTMLButtonElement>) => {
        let promise: Promise<AxiosResponse<string>> = callPUT<string>("http://localhost:8080/robotManager/start");
        promise
            .then((axioResp) => {
                props.setStarted(true);
                showMessage("RobotManager started !!")
            })
            .catch((reason) => {
                showMessage("ERROR :" + reason);
                props.setStarted(false);
            });
    };

    const onClickStopButton = (event: React.MouseEvent<HTMLButtonElement>) => {
        let promise: Promise<AxiosResponse<string>> = callPUT<string>("http://localhost:8080/robotManager/stop");
        promise
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
            <button type="button" className="btn btn-primary" onClick={onClickStartButton}>Start</button>
        </div>
        <div className={"col-2"}>
            <button type="button" className="btn btn-danger" onClick={onClickStopButton}>Stop</button>
        </div>
        <div className={"col-8"}>{message}</div>
    </div>);
}