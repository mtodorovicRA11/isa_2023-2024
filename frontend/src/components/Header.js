import React from 'react';
import {Link} from 'react-router-dom';
import './Header.css';
import { useNavigate } from 'react-router-dom';

const Header = ({isAuthenticated}) => {
    const navigate = useNavigate();

    const handleLogout = () => {
        localStorage.removeItem('authToken'); // Remove token
        navigate('/login'); // Redirect to login page
    };
    return (<header className="header">
            <nav>
                <ul>
                    <li><Link to="/home">Home</Link></li>
                    {!isAuthenticated ? (<>
                        <li><Link to="/login">Login</Link></li>
                        <li><Link to="/register">Register</Link></li>
                    </>) : (<>
                        <li><Link to="/profile">Profile</Link></li>
                        <li><Link to="/complain">Complain</Link></li>
                        <li>
                            <a onClick={handleLogout} className="logout-button">Logout</a>
                        </li>
                    </>)}
                </ul>
            </nav>
    </header>);
};

export default Header;
