import React, {useState} from 'react';
import './App.css';
import {Header} from "./components/Header";
import {StateTable} from "./components/StateTable";

function App() {
    const [isStarted, setStarted] = useState(false);
    const [isGameEnded, endGame] = useState(false);

    return (
        <div className="container">
            <div className={"row"}>&nbsp;</div>
            <Header setStarted={setStarted} isGameEnded={isGameEnded}/>
            <div className={"row"}>&nbsp;</div>
            <StateTable isStarted={isStarted} endGame={endGame}/>
        </div>
    );
}

export default App;
