import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

const Header = ({ isAuthenticated }) => {
  return (
    <header className="header">
      <nav>
        <ul>
          {!isAuthenticated ? (
            <>
              <li><Link to="/login">Login</Link></li>
              <li><Link to="/register">Register</Link></li>
            </>
          ) : (
            <>
              <li><Link to="/profile">Profile</Link></li>
              <li><Link to="/appointments">Appointments</Link></li>
            </>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
