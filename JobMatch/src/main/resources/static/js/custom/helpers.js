document.addEventListener("DOMContentLoaded", function () {
    const salarySlider = document.getElementById('salary-range-new');
    const salaryAmountText = document.getElementById('salary-amount-txt');
    const minSalaryInput = document.getElementById('min-salary');
    const maxSalaryInput = document.getElementById('max-salary');
    const form = document.getElementById('job-search-form');

    // Check if the salary slider exists
    if (!salarySlider) {
        console.error('Salary slider element not found.');
        return;
    }

    // Initialize the noUiSlider
    noUiSlider.create(salarySlider, {
        start: [2700, 5000], // Set initial values for min/max salary
        connect: true,
        range: {
            min: 2200,
            max: 5500,
        },
        tooltips: [true, true],
        format: {
            to: value => Math.round(value),
            from: value => Number(value),
        },
    });

    // Update the text input when the slider values change
    salarySlider.noUiSlider.on('update', function (values) {
        salaryAmountText.value = `${values[0]} - ${values[1]}`;
        minSalaryInput.value = values[0]; // Set min salary to hidden input
        maxSalaryInput.value = values[1]; // Set max salary to hidden input
    });

    // Optional: Make sure the values are set before the form is submitted
    form.addEventListener('submit', function () {
        const [min, max] = salarySlider.noUiSlider.get();
        minSalaryInput.value = min; // Ensure min salary value is updated
        maxSalaryInput.value = max; // Ensure max salary value is updated
    });
});
