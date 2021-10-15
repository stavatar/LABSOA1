import axios from "axios";

export  function onAfterInsertRow(row) {
    debugger;
    axios.post("http://localhost:8081/LABSOA1_war_exploded/collection", row)
        .then(res => {
            const persons = res.data;
            debugger;

        })
    debugger;
}
export  function onAfterDeleteRow(row) {
    axios.delete("http://localhost:8081/LABSOA1_war_exploded/collection", row["id"])
        .then(res => {
            const persons = res.data;
            debugger;

        })
    debugger;
}
export  function onAfterSaveCell(row, cellName, cellValue) {
    axios.put("http://localhost:8081/LABSOA1_war_exploded/collection", row)
        .then(res => {
            const persons = res.data;
            debugger;

        })
    debugger;
}