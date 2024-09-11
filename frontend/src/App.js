import React, {useState} from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Home from './components/Home';
import Equipment from './components/Equipment';
import Profile from "./components/Profile";

function App() {
    const [token, setToken] = useState('');
    return (<Router>
            <Routes>
                <Route path="/login" element={<Login setToken={setToken}/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/home" element={<Home token={token}/>}/>
                <Route path="/equipment/:companyId/:id" element={<Equipment token={token}/>}/>
                <Route path="/profile" element={<Profile token={token}/>}/>
            </Routes>
        </Router>);
}

export default App;