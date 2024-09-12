import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './Home.css';
import Header from './Header';
import { useNavigate } from 'react-router-dom';

const Home = ({token}) => {
  const [companies, setCompanies] = useState([]);
  const [selectedCompany, setSelectedCompany] = useState(null);
  const [equipment, setEquipment] = useState([]);
  const [isAuthenticated, setIsAuthenticated] = useState(false);

  useEffect(() => {
    const checkAuthStatus = () => {
      const token = localStorage.getItem('authToken');
      setIsAuthenticated(!!token);
    };

    checkAuthStatus();
  }, []);

  useEffect(() => {
    axios.get('http://localhost:8080/company/all', {
      headers: {
        'Authorization': `Bearer ${token}`  // Set the Bearer token
      }
    })
      .then(response => setCompanies(response.data))
      .catch(error => console.error('Error fetching companies:', error));
  }, []);

  useEffect(() => {
    if (selectedCompany) {
      axios.get(`http://localhost:8080/equipment/company/${selectedCompany.id}`, {
        headers: {
          'Authorization': `Bearer ${token}`  // Set the Bearer token
        }
      })
        .then(response => setEquipment(response.data))
        .catch(error => console.error('Error fetching equipment:', error));
    }
  }, [selectedCompany]);


  const handleCompanySelect = (company) => {
    setSelectedCompany(company);
    navigate(`/equipment/${company.id}`);
  };
  const navigate = useNavigate();

  const handleEquipmentClick = (id) => {
    let companyId = selectedCompany.id;
    navigate(`/equipment/${companyId}/${id}`);
  };

  return (
    <div>
      <Header isAuthenticated={isAuthenticated} />
      <div className="home-container">
        <h1>Companies</h1>
        <ul className="company-list">
          {companies.map(company => (
            <li key={company.id} className="company-item">
              <button className="company-button" onClick={() => handleCompanySelect(company)}>
                {company.name}
              </button>
            </li>
          ))}
        </ul>
        
        {selectedCompany && (
          <div>
            <h2>Equipment for {selectedCompany.name}</h2>
            <ul className="equipment-list">
              {equipment.map(eq => (
                  <li key={eq.id} className="equipment-item" onClick={() => handleEquipmentClick(eq.id)}>
                    {eq.name}
                  </li>
              ))}
            </ul>
          </div>
        )}
      </div>
    </div>
  );
};

export default Home;
