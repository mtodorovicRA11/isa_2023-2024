import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const Equipment = ({ match, token }) => {
    const { id } = useParams(); // Extract 'id' from URL parameters
    const [equipment, setEquipment] = useState(null);
    const [timeslots, setTimeslots] = useState([]);
    const [selectedTimeslot, setSelectedTimeslot] = useState(null);
    const [reservationStatus, setReservationStatus] = useState('');

    useEffect(() => {
        const fetchEquipment = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/equipment/equipment/${id}`, {
                    headers: {
                        'Authorization': `Bearer ${token}`  // Set the Bearer token
                    }
                });
                setEquipment(response.data);
            } catch (error) {
                console.error('Error fetching equipment:', error);
            }
        };

        const fetchTimeslots = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/equipment/equipment/${id}/timeslots`, {
                    headers: {
                        'Authorization': `Bearer ${token}`  // Set the Bearer token
                    }
                });
                setTimeslots(response.data);
            } catch (error) {
                console.error('Error fetching timeslots:', error);
            }
        };

        fetchEquipment();
        fetchTimeslots();
    }, [id]);

    const handleReserve = async () => {
        if (selectedTimeslot) {
            try {
                const response = await axios.post(`http://localhost:8080/equipment/equipment/${id}/reserve`, null, {
                    params: { timeslotId: selectedTimeslot },
                    headers: {
                        'Authorization': `Bearer ${token}`  // Set the Bearer token
                    }
                });
                setReservationStatus(response.data);
                setSelectedTimeslot(null);
                const updatedTimeslots = await axios.get(`http://localhost:8080/equipment/equipment/${id}/timeslots`, {
                    headers: {
                        'Authorization': `Bearer ${token}`  // Set the Bearer token
                    }
                });
                setTimeslots(updatedTimeslots.data);
            } catch (error) {
                console.error('Error reserving equipment:', error);
                setReservationStatus('Reservation failed');
            }
        } else {
            setReservationStatus('No timeslot selected');
        }
    };

    return (
        <div className="reservation-container">
            {equipment && <h2>Reserve {equipment.name}</h2>}
            <h3>Available Timeslots</h3>
            <ul className="timeslot-list">
                {timeslots.map(timeslot => (
                    <li key={timeslot.id} className="timeslot-item">
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
                    </li>
                ))}
            </ul>
            <button onClick={handleReserve} className="reserve-button">Reserve</button>
            {reservationStatus && <p className="reservation-status">{reservationStatus}</p>}
        </div>
    );
};

export default Equipment;
