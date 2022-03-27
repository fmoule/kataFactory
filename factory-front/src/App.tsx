import React, {useState} from 'react';
import './App.css';
import {Header} from "./components/Header";
import {StateTable} from "./components/StateTable";

function App() {
    const [isStarted, setStarted] = useState(false);

    return (
        <div className="container">
            <div className={"row"}>&nbsp;</div>
            <Header setStarted={setStarted}/>
            <div className={"row"}>&nbsp;</div>
            <StateTable isStarted={isStarted}/>
        </div>
    );
}

export default App;
