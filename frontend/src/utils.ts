import {Measurement} from "./app/model/measurement.model";

export function dateInYyyyMmDdHhMmSs(date: Date, dateDiveder: string = "."): string {
  const dateFormat = new Date(date);
  return (
    [
      padTwoDigits(dateFormat.getDate()),
      padTwoDigits(dateFormat.getMonth() + 1),
      dateFormat.getFullYear(),
    ].join(dateDiveder) +
    " " +
    [
      padTwoDigits(dateFormat.getHours()),
      padTwoDigits(dateFormat.getMinutes()),
      padTwoDigits(dateFormat.getSeconds()),
    ].join(":")
  );
}

export function padTwoDigits(num: number): string {
  return num.toString().padStart(2, "0");
}

export function convertDuration(val: number): string {
  const hours = Math.floor(val / (1000 * 60 * 60));
  const minutes = Math.floor((val % (1000 * 60 * 60)) / (1000 * 60));
  return hours + " Std. " + minutes + " Min.";
}

export function getAverageSpeed(arr: number[]): number {
  let total = 0;
  for (let speed of arr) {
    total += speed;
  }
  return Math.round(total / arr.length);
}

export function getAverage(val: number, divisor: number): number {
  return Math.round(val / divisor);
}

export function getPointsForPath(measurements: Measurement[]): Object[] {
  const pathsArr = [];

  // Filter and map measurements to lat/lng objects
  for (const measurement of measurements) {
    if (measurement.measurementType === "LocationMeasurement" && !measurement.isError) {
      pathsArr.push({
        lat: measurement.latitude,
        lng: measurement.longitude,
        timestamp: new Date(measurement.timestamp)
      });
    }
  }

  // Sort pathsArr by timestamp
  const sortedPathArr = pathsArr.sort((a, b) => a.timestamp.getTime() - b.timestamp.getTime());

  return sortedPathArr;
}
