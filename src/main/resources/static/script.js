// Event listener to update the tierLabel when tierSlider value changes
const tierSlider = document.getElementById("tierSlider");
const tierLabel = document.querySelector('label[for="tierSlider"]');
const tierValueSpan = document.getElementById("tierValue");
tierSlider.addEventListener("input", () => {
    tierLabel.textContent = `Select champion tier: ${tierSlider.value}`;
    tierValueSpan.textContent = tierSlider.value;
});

//EventListener to change the levelLabel and playerLevels input when levelSlider changes value
const levelSlider = document.getElementById("levelSlider");
const levelLabel = document.querySelector('label[for="levelSlider"]');
const playerLevels = document.getElementById("playerLevels");
levelSlider.addEventListener("input", () => {
    levelLabel.textContent = `Select your level: ${levelSlider.value}`;
    playerLevels.value = `${levelSlider.value}-${levelSlider.value}-${levelSlider.value}-${levelSlider.value}-${levelSlider.value}-${levelSlider.value}-${levelSlider.value}`
});

//Remove non-numeric and non-minus sign characters
playerLevels.addEventListener("input", function (event) {
    event.target.value = event.target.value.replace(/[^0-9-]/g, "");
});

//EventListener to always select the text when clicking on an input field
const inputFields = document.getElementsByClassName("form-control");
for (const inputField of inputFields) {
    inputField.addEventListener("click", function (event) {
        event.target.select();
    });
}

//Function to disable and enable fields depending on selection
function handleSelectChange(selectElement) {
    const div = selectElement.closest(".tab-pane");
    const numberFields = div.querySelectorAll('input[type="number"]');

    if (selectElement.options[0].selected) {
        numberFields.forEach(field => {
            field.disabled = true;
            field.value = "";
        });
    } else {
        numberFields.forEach(field => {
            field.disabled = false;
            field.value = 0;
        });
    }
}

//Handle submits with a regex if data is incorrectly entered
document.getElementById("form").addEventListener("submit", function(event) {
    if (/^(?:[1-9]|1[0-1])(?:-(?:[1-9]|1[0-1])){0,6}$/.test(playerLevels.value)) {
        submitForm(event);
    } else {
        alert("Please input the levels as follows: level-level-level (up to 7 levels) where each level is a number between 1 and 11");
    }
});

//Submits the data from the form to be sent to the server
function submitForm(event) {
    event.preventDefault();

    const formData = new FormData(event.target);

    fetch('submit', {
        method: 'POST',
        body: formData
    })
        .then(response => response.text())
        .then(data => {
            populateChart(data);
        })
        .catch(error => {
            alert("The combination of data chosen cannot be simulated.");
        });
}

const chartDom = document.getElementById('chart');
const myChart = echarts.init(chartDom, 'dark');
let option;

//EventListener to resize the chart as the window is being resized as well
window.addEventListener('resize', function () {
    myChart.resize();
});

function populateChart(data) {

    const resultArray = [];
    //Convert data from String to an array of numbers
    const parsedData = JSON.parse(data);
    const rolls =  parsedData.map(numberString => parseInt(numberString, 10));

    //Extract the result from the list of rolls
    for (let i = 1; i < 10; i++) {
        const rollIndex = rolls.length / 10 * i;
        const roll = rolls[rollIndex];
        resultArray.push(roll);
    }

    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'shadow'
            },
            formatter: function(params) {
                let percent = params[0].name;
                let rolls = params[0].value;
                let rollsWithGold = rolls * 2;
                return `${percent}\n<span style="color: #3E98C7;">●</span> ${rolls} Rolls\n(${rollsWithGold} Gold)`;
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: ["10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%"],
                axisTick: {
                    alignWithLabel: true
                },
                name: 'Chance of success',
                nameLocation: 'middle',
                nameGap: 25
            }
        ],
        yAxis: [
            {
                type: 'value',
                name: 'Rolls',
                nameLocation: 'middle',
                nameGap: 25
            }
        ],
        series: [
            {
                name: 'Rolls:',
                type: 'bar',
                barWidth: '60%',
                data: resultArray
            }
        ]
    };

    option && myChart.setOption(option);
}

//Initialize the graph as empty
option = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'shadow'
        },
        formatter: function(params) {
            let percent = params[0].name;
            let rolls = params[0].value;
            let rollsWithGold = rolls * 2;
            return `${percent}\n<span style="color: #3E98C7;">●</span> ${rolls} Rolls\n(${rollsWithGold} Gold)`;
        }
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    xAxis: [
        {
            type: 'category',
            data: ["10%", "20%", "30%", "40%", "50%", "60%", "70%", "80%", "90%"],
            axisTick: {
                alignWithLabel: true
            },
            name: 'Chance of success',
            nameLocation: 'middle',
            nameGap: 25
        }
    ],
    yAxis: [
        {
            type: 'value',
            name: 'Rolls',
            nameLocation: 'middle',
            nameGap: 25
        }
    ],
    series: [
        {
            name: 'Rolls:',
            type: 'bar',
            barWidth: '60%',
            data: [0,0,0,0,0,0,0,0,0]
        }
    ]
};

option && myChart.setOption(option);
