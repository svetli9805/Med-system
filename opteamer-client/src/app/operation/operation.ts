export interface Operation {
  id: string;
  operationType: { name: string };
  patient: { id: string; name: string };
  teamMembers: { id: string; name: string }[];
  state: string;
  startDate: string;
}
