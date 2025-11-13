import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CustomCartItemListComponent } from './custom-cart-item.component';

describe('CustomCartItemComponent', () => {
  let component: CustomCartItemListComponent;
  let fixture: ComponentFixture<CustomCartItemListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ CustomCartItemListComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CustomCartItemListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
