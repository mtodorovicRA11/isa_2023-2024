import React, {useEffect, useState} from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import Home from './components/Home';
import Equipment from './components/Equipment';
import Profile from "./components/Profile";
import PrivateRoute from './components/PrivateRoute';

function App() {
    const [token, setToken] = useState(() => localStorage.getItem('authToken') || '');
    useEffect(() => {
        if (!token) {
            const storedToken = localStorage.getItem('authToken');
            if (storedToken) {
                setToken(storedToken);
            }
        }
    }, [token]);
    return (<Router>
            <Routes>
                <Route path="/login" element={<Login setToken={setToken}/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/home" element={<Home token={token}/>}/>
                <Route path="/" element={<PrivateRoute />}>
                    <Route path="/equipment/:companyId/:id" element={<Equipment token={token}/>} />
                    <Route path="/equipment/:companyId/" element={<Equipment token={token}/>} />
                    <Route path="/equipment/" element={<Equipment token={token}/>} />
                    <Route path="/" element={<Home />} />
                </Route>
                <Route path="/" element={<PrivateRoute />}>
                    <Route path="/profile" element={<Profile token={token}/>}/>
                    <Route path="/" element={<Home />} />
                </Route>
            </Routes>
        </Router>);
}

export default App;