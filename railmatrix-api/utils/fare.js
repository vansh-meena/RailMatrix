// utils/fare.js

/**
 * Calculates the total fare for a single passenger based on the Indian Railways rules.
 * 
 * @param {Object} params
 * @param {number} params.baseFare - The base fare setup for the train (e.g. 50)
 * @param {number} params.farePerKm - The fare multiplier per km (e.g. 1.2)
 * @param {number} params.distanceKm - Total distance for the journey
 * @param {string} params.trainType - 'Superfast', 'Express', 'Passenger'
 * @param {string} params.classCode - '1AC', '2AC', '3AC', 'SL'
 * @returns {number} Final calculated fare rounded to nearest integer
 */
function calculateFare({ baseFare, farePerKm, distanceKm, trainType, classCode }) {
    // 1. Calculate the raw distance base
    const rawBase = parseFloat(baseFare) + (parseFloat(farePerKm) * distanceKm);

    // 2. Class Multiplier (Scaled down for realism)
    const classMultipliers = { '1AC': 3.0, '2AC': 2.0, '3AC': 1.5, 'SL': 1.0 };
    const mult = classMultipliers[classCode] || 1.0;
    
    let fare = rawBase * mult;

    // 3. Reservation Charge
    const resCharges = { '1AC': 60, '2AC': 50, '3AC': 40, 'SL': 20 };
    fare += (resCharges[classCode] || 15);

    // 4. Superfast Charge
    if (trainType === 'Superfast') {
        const sfCharges = { '1AC': 75, '2AC': 45, '3AC': 45, 'SL': 30 };
        fare += (sfCharges[classCode] || 15);
    }

    // 5. GST (5% on AC Classes)
    if (['1AC', '2AC', '3AC'].includes(classCode)) {
        fare += (fare * 0.05);
    }

    return Math.round(fare);
}

module.exports = { calculateFare };
