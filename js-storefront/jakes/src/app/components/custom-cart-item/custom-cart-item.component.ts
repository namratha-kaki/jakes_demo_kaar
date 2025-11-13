import { Component, OnInit } from '@angular/core';
import { CartItemListComponent } from '@spartacus/cart/base/components';
import {
  Cart,
  OrderEntry,
} from '@spartacus/cart/base/root';
import {
  UntypedFormControl,
  AbstractControl,
} from '@angular/forms';


@Component({
  selector: 'cx-cart-item-list',
  templateUrl: './custom-cart-item.component.html',
  styleUrls: ['./custom-cart-item.component.scss'],
})
export class CustomCartItemListComponent extends CartItemListComponent implements OnInit {

 override ngOnInit(): void {
  console.log(' Initial items array in template:', this.items);

  super.ngOnInit?.();

  console.log('CustomCartItemListComponent initialized. Listening for cart changes...');
  this.activeCartService.getActive().subscribe((cart: Cart) => {
    if (cart?.entries?.length) {
      console.log(' Cart Data:', cart);
      this.items = cart.entries;
      console.log('Updated items from cart:', this.items);

      this.items.forEach((entry: OrderEntry) => {
        const stock = entry?.product?.stock?.stockLevel;
        console.log(` Product: ${entry?.product?.code}, Stock Level: ${stock}`);
      });

      this.cd.detectChanges();
    }
  });
}

  isLowStock(entry: OrderEntry): boolean {
  const stock = entry?.product?.stock?.stockLevel;
  return typeof stock === 'number' && stock > 0 && stock < 500;
}
  getQuantityControl(control: AbstractControl | null): UntypedFormControl {
  return control?.get('quantity') as UntypedFormControl;
}

}
