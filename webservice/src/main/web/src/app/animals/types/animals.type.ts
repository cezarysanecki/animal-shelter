import { AnimalKind } from "@animals/enums";

export type AnimalsResult = {
  animals: AnimalShortInfo[];
}

export type AnimalShortInfo = {
  id: number;
  name: string;
  kind: AnimalKind;
  age: number;
  inShelter: boolean;
}

export type AnimalDetails = {
  id: number;
  name: string;
  kind: AnimalKind;
  age: number;
  "admittedAt": string,
  "adoptedAt": string
}
