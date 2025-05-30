let currentInput = '0';
let previousInput = '';
let operation = null;
let resetScreen = false;

const resultDisplay = document.getElementById('result');
const operationDisplay = document.getElementById('operation');

function updateDisplay() {
    resultDisplay.textContent = currentInput;
    operationDisplay.textContent = previousInput + (operation ? ` ${operation} ` : '');
}

function appendNumber(number) {
    if (currentInput === '0' || resetScreen) {
        currentInput = number;
        resetScreen = false;
    } else {
        currentInput += number;
    }
    updateDisplay();
}

function appendOperator(op) {
    if (operation !== null) calculate();
        previousInput = currentInput;
        operation = op;
        resetScreen = true;
        updateDisplay();
}

function calculate() {
    let computation;
    const prev = parseFloat(previousInput);
    const current = parseFloat(currentInput);
            
    if (isNaN(prev) || isNaN(current)) return;
            
    switch (operation) {
    case '+':
        computation = prev + current;
        break;
    case '-':
        computation = prev - current;
        break;
    case '*':
        computation = prev * current;
        break;
    case '/':
        computation = prev / current;
        break;
    case '%':
        computation = prev % current;
        break;
    default:
        return;
    }
            
    currentInput = computation.toString();
    operation = null;
    previousInput = '';
    resetScreen = true;
    updateDisplay();
}

function clearAll() {
    currentInput = '0';
    previousInput = '';
    operation = null;
    updateDisplay();
}

function deleteLast() {
    if (currentInput.length === 1 || (currentInput.length === 2 && currentInput.startsWith('-'))) {
    currentInput = '0';
    } else {
        currentInput = currentInput.slice(0, -1);
    }
    updateDisplay();
}

document.addEventListener('keydown', (e) => {
    if (e.key >= '0' && e.key <= '9') appendNumber(e.key);
    else if (e.key === '.') appendNumber('.');
    else if (e.key === '+' || e.key === '-' || e.key === '*' || e.key === '/' || e.key === '%') 
        appendOperator(e.key);
    else if (e.key === 'Enter' || e.key === '=') calculate();
    else if (e.key === 'Escape') clearAll();
    else if (e.key === 'Backspace') deleteLast();
});

updateDisplay();