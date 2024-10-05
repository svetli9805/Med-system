import { OperationRoom } from '../operationroom/operationroom';
import { Asset } from './asset';

export interface RoomInventory {
  operationRoom: OperationRoom;
  asset: Asset;
  count: number;
}
