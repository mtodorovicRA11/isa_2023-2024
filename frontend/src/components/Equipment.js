import React, {useState, useEffect} from 'react';
import axios from 'axios';
import {useParams} from 'react-router-dom';
import './Equipment.css';
import Header from "./Header";

const Equipment = ({token}) => {
    const {companyId, id} = useParams();
    const [equipment, setEquipment] = useState(null);
    const [timeslots, setTimeslots] = useState([]);
    const [selectedTimeslot, setSelectedTimeslot] = useState(null);
    const [reservationStatus, setReservationStatus] = useState('');
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    useEffect(() => {
        const checkAuthStatus = () => {
            const token = localStorage.getItem('authToken');
            setIsAuthenticated(!!token);
        };

        checkAuthStatus();
    }, []);

    useEffect(() => {
        axios.get(`http://localhost:8080/equipment/${id}`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                setEquipment(response.data);
            })
            .catch(error => {
                console.error('Error fetching equipment:', error);
            });

        axios.get(`http://localhost:8080/equipment/${id}/timeslots`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(response => {
                setTimeslots(response.data);
            })
            .catch(error => {
                console.error('Error fetching timeslots:', error);
            });
    }, [id, token]);

    const handleReserve = () => {
        if (selectedTimeslot) {
            axios.post(`http://localhost:8080/equipment/${id}/reserve`, null, {
                params: {
                    timeslotId: selectedTimeslot,
                    companyId: companyId
                },
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(response => {
                    setReservationStatus(response.data);
                    setSelectedTimeslot(null);
                    return axios.get(`http://localhost:8080/equipment/${id}/timeslots`, {
                        headers: {
                            'Authorization': `Bearer ${token}`
                        }
                    });
                })
                .then(response => {
                    setTimeslots(response.data);
                })
                .catch(error => {
                    console.error('Error reserving equipment:', error);
                    setReservationStatus('Reservation failed');
                });
        } else {
            setReservationStatus('No timeslot selected');
        }
    };

    return (<div><Header isAuthenticated={isAuthenticated}/>
            <div className="reservation-container">
                {equipment && <h2>Reserve {equipment.name}</h2>}
                <h3>Available Timeslots</h3>
                <ul className="timeslot-list">
                    {timeslots.map(timeslot => (<li key={timeslot.id} className="timeslot-item">
                            <label>
                                <input
                                    type="radio"
                                    name="timeslot"
                                    value={timeslot.id}
                                    checked={selectedTimeslot === timeslot.id}
                                    onChange={() => setSelectedTimeslot(timeslot.id)}
                                />
                                {timeslot.startTime} - {timeslot.endTime}
                            </label>
                        </li>))}
                </ul>
                <button onClick={handleReserve} className="reserve-button">Reserve</button>
                {reservationStatus && <p className="reservation-status">{reservationStatus}</p>}
            </div>
        </div>);
};

export default Equipment;
