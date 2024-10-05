import { OperationProvider } from '../operation/operationprovider';

export interface TeamMember {
  id: string;
  name: string;
  operationProvider: OperationProvider;
}
