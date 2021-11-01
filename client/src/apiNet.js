import axios from "axios";
import {NotificationManager} from "react-notifications";
export const path_server="http://localhost:8181/LABSOA1_war/";
//export  const path_server = "http://desktop-biptr25:8085/Servlets-1.0-SNAPSHOT/";
//export  const path_server = "http://localhost:8181/Servlets-1.0-SNAPSHOT";


function printMessages(msg){
    debugger;
    if(msg.code===0){
        NotificationManager.error( msg.data, 'Ошибка',2000);
    }else {
        NotificationManager.success( msg.data, 'Успех!',2000);
    }
}
export function deleteMood(mood,hist){
    axios.post(path_server+"deleteAllForMood",null,{params:{"mood": mood}})
        .then(res => {

            const msg = res.data;
            printMessages(msg);
            window.location.reload();
        })

}


export function calcAverage(){
    axios.get(path_server+"averageSpeed")
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
        })
}

export function  countMood(mood){
    axios.get(path_server+"countHuman",{params:{"mood": mood}})
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
        })
}

export  function addObj(new_obj,hist){
    axios.post(path_server+"humanBeings", new_obj)
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
            hist.push("/");

        })
}
export  function onAfterDeleteRow(row) {
    let g = row.length;
    for(let i=0;i<g;i++){
        axios.delete(path_server+"humanBeings/"+row[i])
            .then(res => {
                debugger;
                const msg = res.data;
                printMessages(msg);
                window.location.reload();

            })
    }
    debugger;
}
export  function sendEditCell(row, cellName, cellValue) {
    debugger;

    axios.put(path_server+"humanBeings",{id:row["id"],fieldName:cellName,newValue: cellValue})
        .then(res => {
            debugger;
            const msg = res.data;
            printMessages(msg);
            window.location.reload();
        })

}