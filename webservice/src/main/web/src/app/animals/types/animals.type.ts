import { AnimalKind } from "@animals/enums";

export type AnimalShortInfo = {
  id: number;
  name: string;
  translocoKind: string;
  imageUrl: string;
  age: number;
  inShelter: boolean;
}

export type AnimalDetails = {
  id: number;
  name: string;
  translocoKind: string;
  imageUrl: string;
  age: number;
  admittedAt: string,
  adoptedAt: string
}
